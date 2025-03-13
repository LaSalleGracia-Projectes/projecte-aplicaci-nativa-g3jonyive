package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.PhoneAuthCredential
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _fullname: MutableState<String> = mutableStateOf("")
    val fullname: State<String> get() = _fullname

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> get() = _email

    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val phoneNumber: State<String> get() = _phoneNumber

    private val _username: MutableState<String> = mutableStateOf("")
    val username: State<String> get() = _username

    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String> get() = _password

    fun onRegister(onRegisterComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(_email.value, _password.value)

                result.user?.updateProfile(displayName = _fullname.value)

                println("Usuari creat amb èxit!")

                onRegisterComplete()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun updateUserDetails(fullname: String, phoneNumber: String, username: String) {
        _fullname.value = fullname
        _phoneNumber.value = phoneNumber
        _username.value = username
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePhoneNumber(newPhoneNumber: String) {
        if (newPhoneNumber.length > 9) return
        _phoneNumber.value = newPhoneNumber
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateFullname(newFullname: String) {
        _fullname.value = newFullname
    }

    fun isValidEmail(): Boolean = _email.value.contains("@") && _email.value.contains(".")

    fun isValidPassword(): Boolean =
        _password.value.length >= 8 && _password.value.any { it.isUpperCase() } && _password.value.any { it.isLowerCase() } &&
                _password.value.any { it.isDigit() } && _password.value.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }

    fun isValidPhoneNumber(): Boolean = _phoneNumber.value.length == 9 && _phoneNumber.value.all { it.isDigit() }

    fun isValidRegister(): Boolean = isValidEmail() && isValidPassword() && isValidPhoneNumber() && _fullname.value.isNotEmpty()
}
