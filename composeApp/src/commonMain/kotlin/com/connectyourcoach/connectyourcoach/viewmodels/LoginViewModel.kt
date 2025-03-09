package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
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



    fun onEmailChange(username: String) {
        _email.value = username
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onLogin(): Boolean{
        _loading.value = true

        var state = false

        viewModelScope.launch {
            try {
                val authResult = auth.value.signInWithEmailAndPassword(
                    email = _email.value,
                    password = _password.value
                )

                authResult.user?.let {
                    _loading.value = false
                    _error.value = "Session started"
                    state = true
                } ?: run {
                    _loading.value = false
                    _error.value = "Invalid email or password"
                    state = false
                }
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message ?: "An unknown error occurred"
                state = false
            }
        }

        return state
    }
}