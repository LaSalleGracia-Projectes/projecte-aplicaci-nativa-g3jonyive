package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _username: MutableState<String> = mutableStateOf("")
    val username: MutableState<String> get() = _username

    private val _password: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> get() = _password

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> get() = _loading

    private val _error: MutableState<String> = mutableStateOf("")
    val error: MutableState<String> get() = _error



    fun onUsernameChange(username: String) {
        _username.value = username
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onLogin(): Boolean{
        val status = username.value.isNotEmpty() && password.value.isNotEmpty()

        viewModelScope.launch {
            _loading.value = true

            // Simulamos un login
            kotlinx.coroutines.delay(2000)

            if (status) {
                _error.value = ""
            } else {
                _error.value = "Invalid username or password"
            }

            _loading.value = false
        }

        return status
    }
}