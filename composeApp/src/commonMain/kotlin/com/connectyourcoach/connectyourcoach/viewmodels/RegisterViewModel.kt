package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.connectyourcoach.connectyourcoach.model.UserData
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel {
    var fullname by mutableStateOf("")
    var email by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var avatarUrl by mutableStateOf("")
    var registerError by mutableStateOf("")
    var showAvatarOptions by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    fun onRegister(onSuccess: () -> Unit) {
        isLoading = true
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password)
                result.user?.updateProfile(displayName = fullname, photoURL = avatarUrl)
                onSuccess()
            } catch (e: Exception) {
                registerError = "Registration error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun generateRandomAvatar() {
        avatarUrl = "https://api.dicebear.com/7.x/pixel-art/svg?seed=${System.currentTimeMillis()}"
    }

    fun isValidEmail(): Boolean = email.contains("@") && email.contains(".")

    fun isValidPassword(): Boolean =
        password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() } &&
                password.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }

    fun isValidPhoneNumber(): Boolean = phoneNumber.length == 9 && phoneNumber.all { it.isDigit() }

    fun isValidRegister(): Boolean =
        isValidEmail() &&
                isValidPassword() &&
                isValidPhoneNumber() &&
                fullname.isNotEmpty() &&
                username.isNotEmpty() &&
                avatarUrl.isNotEmpty()

    fun toUserData(): UserData {
        return UserData(
            uid = Firebase.auth.currentUser?.uid ?: "",
            displayName = fullname,
            email = email,
            phoneNumber = phoneNumber,
            photoUrl = avatarUrl,
            username = username
        )
    }
}