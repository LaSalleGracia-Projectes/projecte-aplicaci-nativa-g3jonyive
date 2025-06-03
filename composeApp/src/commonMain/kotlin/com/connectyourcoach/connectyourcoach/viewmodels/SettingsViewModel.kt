package com.connectyourcoach.connectyourcoach.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

data class ProfileUiState(
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val photoUrl: String? = null,
    val photoUri: Uri? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = auth.currentUser
        _uiState.value = _uiState.value.copy(
            displayName = user?.displayName ?: "",
            email = user?.email ?: "",
            photoUrl = user?.photoUrl?.toString()
        )
    }

    fun onNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(displayName = newName)
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onImageSelected(uri: Uri) {
        _uiState.value = _uiState.value.copy(photoUri = uri)
    }

    fun saveChanges(context: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, message = null)

            val user = auth.currentUser ?: return@launch

            try {
                val updates = UserProfileChangeRequest.Builder()
                    .setDisplayName(_uiState.value.displayName)
                    .apply {
                        if (_uiState.value.photoUri != null) {
                            val url = uploadImageToStorage(_uiState.value.photoUri!!)
                            setPhotoUri(Uri.parse(url))
                        }
                    }.build()

                user.updateProfile(updates).await()

                if (user.email != _uiState.value.email) {
                    user.updateEmail(_uiState.value.email).await()
                }

                if (_uiState.value.password.isNotBlank()) {
                    user.updatePassword(_uiState.value.password).await()
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Perfil actualizado exitosamente"
                )
                loadUserData()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Error: ${e.message}"
                )
            }
        }
    }

    private suspend fun uploadImageToStorage(uri: Uri): String {
        val fileName = "profile_images/${UUID.randomUUID()}.jpg"
        val ref = storage.reference.child(fileName)
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }

    fun logout(context: Context) {
        auth.signOut()
        _uiState.value = _uiState.value.copy(message = "Sesión cerrada")
    }

    fun deleteAccount(context: Context) {
        viewModelScope.launch {
            try {
                auth.currentUser?.delete()?.await()
                _uiState.value = _uiState.value.copy(message = "Cuenta eliminada")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(message = "Error: ${e.message}")
            }
        }
    }
}
