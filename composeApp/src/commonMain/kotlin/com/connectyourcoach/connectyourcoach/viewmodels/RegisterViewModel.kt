package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _username: MutableState<String> = mutableStateOf("")
    val username: State<String> get() = _username

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password: MutableState<String> = mutableStateOf("")
    val password: State<String> get() = _password

    private val _registerError: MutableState<String> = mutableStateOf("")
    val registerError: State<String> get() = _registerError

    fun onRegister(onRegisterComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(_email.value, _password.value)
                result.user?.updateProfile(displayName = _username.value)
                println("Usuari creat amb èxit!")
                onRegisterComplete()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun isValidEmail(): Boolean = _email.value.contains("@") && _email.value.contains(".")

    fun isValidPassword(): Boolean =
        _password.value.length >= 8 && _password.value.any { it.isUpperCase() } && _password.value.any { it.isLowerCase() } &&
                _password.value.any { it.isDigit() } && _password.value.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }

    fun isValidRegister(): Boolean = isValidEmail() && isValidPassword() && _username.value.isNotEmpty()
}