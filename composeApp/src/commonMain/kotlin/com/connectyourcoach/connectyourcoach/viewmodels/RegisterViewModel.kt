package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.apicamera.ImageLoader
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _username = mutableStateOf("")
    val username: State<String> get() = _username

    private val _fullName = mutableStateOf("")
    val fullName: State<String> get() = _fullName

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> get() = _phoneNumber

    private val _birthDate = mutableStateOf("")
    val birthDate: State<String> get() = _birthDate

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _registerError = mutableStateOf("")
    val registerError: State<String> get() = _registerError

    private val _photoUrl = mutableStateOf("")
    val photoUrl: State<String> get() = _photoUrl

    private val _showAvatarGenerator = mutableStateOf(false)
    val showAvatarGenerator: State<Boolean> get() = _showAvatarGenerator

    private val _isAvatarGenerated = mutableStateOf(false)
    val isAvatarGenerated: State<Boolean> get() = _isAvatarGenerated

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val repository = UserRepository();

    private var imageLoader: ImageLoader? = null

    fun initialize(httpClient: HttpClient) {
        imageLoader = ImageLoader(httpClient)
        viewModelScope.launch {
            generateRandomAvatar()
        }
    }

    suspend fun generateRandomAvatar() {
        try {
            _isAvatarGenerated.value = false
            if (imageLoader == null) {
                _registerError.value = "ImageLoader no inicialitzat"
                return
            }
            val newAvatarUrl = imageLoader!!.getRandomAvatar()
            _photoUrl.value = newAvatarUrl
            _isAvatarGenerated.value = true
        } catch (e: Exception) {
            _registerError.value = "Error en generar l'avatar: ${e.message}"
        }
    }

    fun onGenerateRandomAvatar() {
        viewModelScope.launch {
            generateRandomAvatar()
        }
    }

    fun onRegister(onRegisterComplete: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(_email.value, _password.value)
                result.user?.updateProfile(
                    displayName = _username.value,
                    photoUrl = _photoUrl.value
                )
                onRegisterComplete()
            } catch (e: Exception) {
                updateRegisterError("Error en el registre: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updateFullName(newFullName: String) {
        _fullName.value = newFullName
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        _phoneNumber.value = newPhoneNumber
    }

    fun updateBirthDate(newBirthDate: String) {
        _birthDate.value = newBirthDate
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateAvatarUrl(newAvatarUrl: String) {
        _photoUrl.value = newAvatarUrl
    }

    fun updateRegisterError(errorMessage: String) {
        _registerError.value = errorMessage
    }

    fun showAvatarGenerator(show: Boolean) {
        _showAvatarGenerator.value = show
    }

    fun isValidEmail(): Boolean = _email.value.contains("@") && _email.value.contains(".")

    fun isValidPassword(): Boolean =
        _password.value.length >= 8 &&
                _password.value.any { it.isUpperCase() } &&
                _password.value.any { it.isLowerCase() } &&
                _password.value.any { it.isDigit() } &&
                _password.value.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }

    fun isValidRegister(): Boolean =
        isValidEmail() &&
                isValidPassword() &&
                _username.value.isNotEmpty() &&
                _photoUrl.value.isNotEmpty()
}