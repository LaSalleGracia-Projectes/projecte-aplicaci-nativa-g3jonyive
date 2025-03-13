package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    private val _fullName: MutableState<String> = mutableStateOf("")
    val fullname: MutableState<String> get() = _fullName

    private val _email: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> get() = _email

    private val _password: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> get() = _password

    private val _saveError: MutableState<String?> = mutableStateOf(null)
    val saveError: MutableState<String?> get() = _saveError

    private val _saved: MutableState<Boolean> = mutableStateOf(false)
    val saved: MutableState<Boolean> get() = _saved

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> get() = _loading

    init {
        if (auth.currentUser != null) {
            if (!auth.currentUser?.displayName.isNullOrEmpty()) {
                _fullName.value = auth.currentUser!!.displayName!!
            }
            if (!auth.currentUser?.email.isNullOrEmpty()) {
                _email.value = auth.currentUser!!.email!!
            }
        }
    }

    fun updateFullname(fullname: String) {
        _fullName.value = fullname
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun isValidEmail(): Boolean = _email.value.contains("@") && _email.value.contains(".")

    fun isValidPassword(): Boolean =
        _password.value.length >= 8 && _password.value.any { it.isUpperCase() } && _password.value.any { it.isLowerCase() } &&
                _password.value.any { it.isDigit() } && _password.value.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }

    fun isValidSave(): Boolean = isValidEmail() && isValidPassword()

    fun onSave() {
        viewModelScope.launch {
            save()
        }
    }

    suspend fun save() {
        try {
            _loading.value = true
            auth.currentUser?.updateProfile(displayName = _fullName.value)
            _loading.value = false
            _saved.value = true
        } catch (e: Exception) {
            _saved.value = false
            _loading.value = false
            _saveError.value = e.message
        }
    }
}