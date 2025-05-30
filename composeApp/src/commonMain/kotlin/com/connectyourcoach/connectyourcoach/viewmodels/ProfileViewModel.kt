class ProfileViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private val _userProfile = mutableStateOf<UserProfile?>(null)
    val userProfile: State<UserProfile?> = _userProfile

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser ?: return
        firestore.collection("users").document(user.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                snapshot?.toObject(UserProfile::class.java)?.let {
                    _userProfile.value = it
                }
            }
    }
}

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val photoUrl: String = ""
)
