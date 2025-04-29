package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: MutableState<FirebaseAuth> = mutableStateOf(Firebase.auth)

    private val _email: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> get() = _email

    private val _password: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> get() = _password

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> get() = _loading

    private val _error: MutableState<String> = mutableStateOf("")
    val error: MutableState<String> get() = _error

    private val _loggedIn: MutableState<Boolean> = mutableStateOf(false)
    val loggedIn: MutableState<Boolean> get() = _loggedIn

    private val _navigateToRegister: MutableState<Boolean> = mutableStateOf(false)
    val navigateToRegister: MutableState<Boolean> get() = _navigateToRegister

    fun onEmailChange(username: String) {
        _email.value = username
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onLogin() {
        _loading.value = true
        _error.value = ""

        viewModelScope.launch {
            try {
                auth.value.signInWithEmailAndPassword(_email.value, _password.value)
                _loggedIn.value = true
            } catch (e: Exception) {
                _error.value = "Error iniciant sessió: ${e.message}"
                _loggedIn.value = false
            } finally {
                _loading.value = false
            }
        }
    }

    fun onForgotPassword() {
        _loading.value = true
        _error.value = ""

        viewModelScope.launch {
            try {
                auth.value.sendPasswordResetEmail(_email.value)
                _error.value = "S'ha enviat un correu per restablir la contrasenya."
            } catch (e: Exception) {
                _error.value = "Error enviant l'email: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun onGoogleLogin(idToken: String) {
        _loading.value = true
        _error.value = ""

        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.credential(idToken, null)
                auth.value.signInWithCredential(credential)
                _loggedIn.value = true
            } catch (e: Exception) {
                _error.value = "Error iniciant sessió amb Google: ${e.message}"
                _loggedIn.value = false
            } finally {
                _loading.value = false
            }
        }
    }
}
