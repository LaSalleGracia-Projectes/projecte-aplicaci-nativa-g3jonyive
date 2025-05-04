package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.apicamera.ImageLoader
import com.connectyourcoach.connectyourcoach.models.CustomException
import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.FirestoreUserRepository
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _username = mutableStateOf("")
    val username: State<String> get() = _username

    private val _usernameError = mutableStateOf("")
    val usernameError: State<String> get() = _usernameError

    private val _fullName = mutableStateOf("")
    val fullName: State<String> get() = _fullName

    private val _fullNameError = mutableStateOf("")
    val fullNameError: State<String> get() = _fullNameError

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> get() = _phoneNumber

    private val _phoneNumberError = mutableStateOf("")
    val phoneNumberError: State<String> get() = _phoneNumberError

    private val _birthDate = mutableStateOf("")
    val birthDate: State<String> get() = _birthDate

    private val _birthDateError = mutableStateOf("")
    val birthDateError: State<String> get() = _birthDateError

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _emailError = mutableStateOf("")
    val emailError: State<String> get() = _emailError

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _passwordError = mutableStateOf("")
    val passwordError: State<String> get() = _passwordError

    private val _photoUrl = mutableStateOf("")
    val photoUrl: State<String> get() = _photoUrl

    private val _registerError = mutableStateOf("")
    val registerError: State<String> get() = _registerError

    private val _isAvatarGenerated = mutableStateOf(false)
    val isAvatarGenerated: State<Boolean> get() = _isAvatarGenerated

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val userRepository = UserRepository();

    private val firestoreUserRepository = FirestoreUserRepository()

    private var imageLoader: ImageLoader? = null

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> get() = _showDialog

    init {
        imageLoader = ImageLoader(
            httpClient = HttpClient(),
        )

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

    fun onRegister() {
        if (!isValidRegister()) {
            updateRegisterError("Please fill all fields correctly")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            resetErrors()

            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(_email.value, _password.value)
                result.user?.updateProfile(
                    displayName = _username.value,
                    photoUrl = _photoUrl.value
                )

                val user = User(
                    username = _username.value,
                    full_name = _fullName.value,
                    phone = _phoneNumber.value,
                    birth_date = _birthDate.value,
                    email = _email.value,
                    profile_picture = _photoUrl.value,
                    uid = result.user?.uid ?: "",
                )

                userRepository.createUser(
                    user = user,
                    onSuccessResponse = {
                        _showDialog.value = true
                    },
                    onErrorResponse = { error ->
                        if (error.exception == CustomException.ValidationError) {
                            if (error.validationError != null && error.validationError.isNotEmpty()) {
                                for (validationError in error.validationError) {
                                    for ((key, value) in validationError) {
                                        when (key) {
                                            "username" -> _usernameError.value = value
                                            "full_name" -> _fullNameError.value = value
                                            "phone" -> _phoneNumberError.value = value
                                            "birth_date" -> _birthDateError.value = value
                                            "email" -> _emailError.value = value
                                            "password" -> _passwordError.value = value
                                        }
                                    }
                                }
                            }
                        }

                        onError(Exception(error.details))
                    },
                    onFinish = {
                        _isLoading.value = false
                    }
                )

                firestoreUserRepository.addUser(
                    user = FirestoreUser(
                        uid = result.user?.uid ?: "",
                        username = _username.value,
                        phone = _phoneNumber.value,
                    )
                )
            } catch (e: Exception) {
                onError(e)
                _isLoading.value = false
            }
        }
    }

    fun onError(e: Exception) {
        viewModelScope.launch {
            updateRegisterError("Error en el registre: ${e.message}")

            userRepository.deleteUser(
                nickname = _username.value,
                token = Firebase.auth.currentUser?.getIdToken(false) ?: "",
                onSuccessResponse = {
                    // User deleted successfully
                },
                onErrorResponse = { error ->
                    // Handle error
                }
            )

            if (Firebase.auth.currentUser != null) {
                Firebase.auth.currentUser?.delete()
            }

            firestoreUserRepository.deleteUser(
                userId = Firebase.auth.currentUser?.uid ?: "",
            )
        }
    }

    fun onDismissDialog() {
        _showDialog.value = false
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

    fun updateRegisterError(errorMessage: String) {
        _registerError.value = errorMessage
    }

    fun resetErrors() {
        _registerError.value = ""
        _usernameError.value = ""
        _fullNameError.value = ""
        _phoneNumberError.value = ""
        _birthDateError.value = ""
        _emailError.value = ""
        _passwordError.value = ""
    }

    fun isValidEmail(): Boolean = _email.value.contains("@") && _email.value.contains(".")

    fun isValidPassword(): Boolean =
        _password.value.length >= 8 &&
                _password.value.any { it.isUpperCase() } &&
                _password.value.any { it.isLowerCase() } &&
                _password.value.any { it.isDigit() } &&
                _password.value.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }

    fun isValidPhone(): Boolean = _phoneNumber.value.length == 9 && _phoneNumber.value.all { it.isDigit() }

    fun isValidaBirthDate(): Boolean {
        val dateParts = _birthDate.value.split("-")
        if (dateParts.size != 3) return false
        val year = dateParts[0].toIntOrNull()
        val month = dateParts[1].toIntOrNull()
        val day = dateParts[2].toIntOrNull()
        if (day == null || month == null || year == null) return false
        return day in 1..31 && month in 1..12 && year > 1900
    }

    fun isValidRegister(): Boolean {
        resetErrors()

        if (_username.value.isEmpty()) {
            _usernameError.value = "Username is required"
        }
        if (_fullName.value.isEmpty()) {
            _fullNameError.value = "Full name is required"
        }
        if (_phoneNumber.value.isEmpty()) {
            _phoneNumberError.value = "Phone number is required"
        } else if (!isValidPhone()) {
            _phoneNumberError.value = "Invalid phone number format"
        }
        if (_birthDate.value.isEmpty()) {
            _birthDateError.value = "Birth date is required"
        } else if (!isValidaBirthDate()) {
            _birthDateError.value = "Invalid birth date format, should be YYYY-MM-DD"
        }
        if (_email.value.isEmpty()) {
            _emailError.value = "Email is required"
        } else if (!isValidEmail()) {
            _emailError.value = "Invalid email format"
        }
        if (_password.value.isEmpty()) {
            _passwordError.value = "Password is required"
        } else if (!isValidPassword()) {
            _passwordError.value = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        }
        if (_photoUrl.value.isEmpty()) {
            _registerError.value = "Photo URL is required"
        }

        return _email.value.isNotEmpty() &&
                isValidEmail() &&
                isValidPhone() &&
                isValidPassword() &&
                isValidaBirthDate() &&
                _password.value.isNotEmpty() &&
                _phoneNumber.value.isNotEmpty() &&
                _birthDate.value.isNotEmpty() &&
                _username.value.isNotEmpty() &&
                _fullName.value.isNotEmpty() &&
                _photoUrl.value.isNotEmpty()
    }
}