import androidx.compose.foundation.text.input.TextFieldState.Saver.save
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import coil3.Uri
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class SettingsViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var photoUri by mutableStateOf<Uri?>(null)
    var photoUrl by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser ?: return
        firestore.collection("users").document(user.save()).get()
            .addOnSuccessListener { snapshot ->
                val profile = snapshot.toObject(UserProfile::class.java)
                if (profile != null) {
                    name = profile.name
                    email = profile.email
                    phone = profile.phone
                    photoUrl = profile.photoUrl
                }
            }
    }

    fun updateProfile(onComplete: (Boolean) -> Unit) {
        val user = auth.currentUser ?: return
        isLoading = true

        fun updateUserData(photoDownloadUrl: String? = null) {
            val data = mutableMapOf<String, Any>(
                "name" to name,
                "email" to email,
                "phone" to phone,
            )
            photoDownloadUrl?.let {
                data["photoUrl"] = it
            }

            firestore.collection("users").document(user.uid)
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    isLoading = false
                    onComplete(true)
                }
                .addOnFailureListener {
                    isLoading = false
                    errorMessage = it.message
                    onComplete(false)
                }
        }

        // Si hay nueva foto, subirla primero
        photoUri?.let { uri ->
            val ref = storage.reference.child("profile_images/${user.uid}.jpg")
            ref.putFile(uri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { url ->
                        updateUserData(url.toString())
                    }
                }
                .addOnFailureListener {
                    isLoading = false
                    errorMessage = it.message
                    onComplete(false)
                }
        } ?: run {
            // Si no hay foto nueva solo actualizamos otros datos
            updateUserData()
        }
    }
}
