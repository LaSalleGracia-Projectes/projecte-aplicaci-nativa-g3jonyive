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
    private val _fullname: MutableState<String> = mutableStateOf("")
    val fullname: State<String> get() = _fullname

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> get() = _email

    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val phoneNumber: State<String> get() = _phoneNumber

    private val _username: MutableState<String> = mutableStateOf("")
    val username: State<String> get() = _username

    fun onRegister(fullname: String, email: String, password: String, phoneNumber: String, username: String) {
        viewModelScope.launch {
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password)

                _fullname.value = fullname
                _email.value = email
                _phoneNumber.value = phoneNumber
                _username.value = username

                println("Usuari creat amb èxit!")
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
}
