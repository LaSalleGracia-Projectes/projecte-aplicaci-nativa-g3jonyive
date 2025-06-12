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

    private val _active = mutableStateOf(true)
    val active: State<Boolean> get() = _active

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
                // Attempt to create user with email and password in Firebase Auth
                val authResult = Firebase.auth.createUserWithEmailAndPassword(_email.value, _password.value)
                val firebaseUser = authResult.user

                firebaseUser?.updateProfile(
                    displayName = _username.value,
                    photoUrl = _photoUrl.value
                )

                val uid = firebaseUser?.uid ?: ""

                // Create user in your custom backend
                val user = User(
                    username = _username.value,
                    full_name = _fullName.value,
                    phone = _phoneNumber.value,
                    birth_date = _birthDate.value,
                    email = _email.value,
                    profile_picture = _photoUrl.value,
                    uid = uid,
                )

                userRepository.createUser(
                    user = user,
                    onSuccessResponse = {
                        _showDialog.value = true
                        _isLoading.value = false // Ensure loading is stopped on success
                    },
                    onErrorResponse = { error ->
                        // Handle validation errors from custom backend
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
                        // Call onError to clean up Firebase Auth and Firestore if custom backend failed
                        onError(Exception(error.details), uid, true)
                        _isLoading.value = false
                    },
                    onFinish = {
                        // This onFinish might be called after onError, so ensure _isLoading is handled correctly.
                        // For clarity, _isLoading is set inside success/error callbacks now.
                    }
                )

                // Add user to Firestore (this should ideally happen after successful custom backend registration)
                if (uid.isNotEmpty()) {
                    firestoreUserRepository.addUser(
                        user = FirestoreUser(
                            uid = uid,
                            username = _username.value,
                            phone = _phoneNumber.value,
                            photoUrl = _photoUrl.value,
                        )
                    )
                } else {
                    // Log or handle the case where Firebase Auth user UID is unexpectedly empty
                    println("RegisterViewModel: Firebase Auth UID is empty after creation.")
                    onError(Exception("Firebase Auth UID is empty after creation"), uid, false)
                }

            } catch (e: Exception) {
                // Catch any exceptions during Firebase Auth user creation
                onError(e, Firebase.auth.currentUser?.uid ?: "", false) // Pass current UID if available
                _isLoading.value = false
            }
        }
    }

    /**
     * Handles errors during registration, performing cleanup actions like deleting the user
     * from Firebase Auth and Firestore if they were partially created.
     * @param e The exception that occurred.
     * @param uidToDelete The UID of the user to attempt to delete from Firestore/Firebase Auth.
     * @param isBackendFailure Indicates if the failure came from the custom backend (UserRepository).
     */
    fun onError(e: Exception, uidToDelete: String, isBackendFailure: Boolean) {
        viewModelScope.launch {
            updateRegisterError("Error en el registre: ${e.message}")

            val currentUser = Firebase.auth.currentUser

            // Only attempt Firebase Auth and Firestore deletion if a user was actually created
            // and we have a UID to work with.
            if (currentUser != null && currentUser.uid == uidToDelete && uidToDelete.isNotEmpty()) {
                // Delete user from custom backend (if it failed earlier, this might not be needed or would re-attempt)
                // This call here is a fallback; the primary call is in onRegister's onErrorResponse.
                if (!isBackendFailure) { // Avoid redundant calls if backend already failed
                    userRepository.deleteUser(
                        nickname = _username.value, // This might not exist in backend if auth failed early
                        token = currentUser.getIdToken(false) ?: "",
                        onSuccessResponse = {
                            // User deleted successfully from custom backend
                        },
                        onErrorResponse = { error ->
                            // Handle error deleting from custom backend
                            println("Error deleting user from custom backend during onError: ${error.details}")
                        }
                    )
                }

                // Delete Firebase Auth user
                try {
                    currentUser.delete()
                } catch (authDeleteException: Exception) {
                    println("Error deleting Firebase Auth user: ${authDeleteException.message}")
                    // Potentially, if delete fails, you might want to force sign out or show a critical error.
                }

                // Delete user from Firestore
                firestoreUserRepository.deleteUser(uidToDelete)
            } else if (uidToDelete.isNotEmpty()) {
                // This block handles cases where the Firebase Auth user might not be 'currentUser'
                // anymore, but we still have a UID from a partial creation to try and clean up in Firestore.
                // Firebase Auth user cannot be deleted without being current user, but Firestore can.
                firestoreUserRepository.deleteUser(uidToDelete)
            } else {
                println("RegisterViewModel: No valid UID or current user to perform cleanup in onError.")
            }
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
        // Simple date validation (does not account for days in month or leap years)
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

        // Return true only if all fields are valid and errors are cleared
        return _usernameError.value.isEmpty() &&
                _fullNameError.value.isEmpty() &&
                _phoneNumberError.value.isEmpty() &&
                _birthDateError.value.isEmpty() &&
                _emailError.value.isEmpty() &&
                _passwordError.value.isEmpty() &&
                _registerError.value.isEmpty() && // Ensure photoUrl requirement is covered by this
                _photoUrl.value.isNotEmpty() // Explicit check for photoUrl
    }
}
