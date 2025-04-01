package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.apicamera.ImageLoader
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    private val _fullName: MutableState<String> = mutableStateOf("")
    val fullname: MutableState<String> get() = _fullName

    private val _email: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> get() = _email

    private val _password: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> get() = _password

    private val _currentPassword: MutableState<String> = mutableStateOf("")
    val currentPassword: MutableState<String> get() = _currentPassword

    private val _saveError: MutableState<String?> = mutableStateOf(null)
    val saveError: MutableState<String?> get() = _saveError

    private val _saved: MutableState<Boolean> = mutableStateOf(false)
    val saved: MutableState<Boolean> get() = _saved

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> get() = _loading

    private val _settingsError: MutableState<String> = mutableStateOf("")
    val registerError: State<String> get() = _settingsError

    private val _showPasswordField: MutableState<Boolean> = mutableStateOf(false)
    val showPasswordField: MutableState<Boolean> get() = _showPasswordField

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
        checkForChanges()
    }

    fun updateEmail(email: String) {
        _email.value = email
        checkForChanges()
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateCurrentPassword(currentPassword: String) {
        _currentPassword.value = currentPassword
    }

    private fun checkForChanges() {
        val user = auth.currentUser
        _showPasswordField.value = _fullName.value != user?.displayName || _email.value != user?.email
    }

    fun isValidEmail(): Boolean = _email.value.contains("@") && _email.value.contains(".")

    fun isValidPassword(): Boolean =
        _password.value.length >= 8 && _password.value.any { it.isUpperCase() } && _password.value.any { it.isLowerCase() } &&
                _password.value.any { it.isDigit() } && _password.value.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }

    fun isValidSave(): Boolean = isValidEmail() && (!_showPasswordField.value || _currentPassword.value.isNotEmpty())

    fun onSave() {
        viewModelScope.launch {
            save()
        }
    }

    private val _avatarUrl: MutableState<String> = mutableStateOf("")
    val avatarUrl: State<String> get() = _avatarUrl

    private val _showAvatarGenerator: MutableState<Boolean> = mutableStateOf(false)
    val showAvatarGenerator: State<Boolean> get() = _showAvatarGenerator

    private lateinit var imageLoader: ImageLoader

    fun initialize(httpClient: HttpClient) {
        imageLoader = ImageLoader(httpClient)
    }

    fun generateRandomAvatar() {
        viewModelScope.launch {
            try {
                val newAvatarUrl = imageLoader.getRandomAvatar()
                _avatarUrl.value = newAvatarUrl
            } catch (e: Exception) {
                _settingsError.value = "Error en generar l'avatar: ${e.message}"
            }
        }
    }


    suspend fun save() {
        try {
            _loading.value = true
            val user = auth.currentUser
            if (user != null) {
                if (_fullName.value != user.displayName) {
                    user.updateProfile(displayName = _fullName.value)
                }
                if (_email.value != user.email) {
                    user.updateEmail(_email.value)
                }
                if (_password.value.isNotEmpty()) {
                    user.updatePassword(_password.value)
                }
            }
            _loading.value = false
            _saved.value = true
        } catch (e: Exception) {
            _saved.value = false
            _loading.value = false
            _saveError.value = e.message
        }
    }

    fun showAvatarGenerator(show: Boolean) {
        _showAvatarGenerator.value = show
    }

    fun updateAvatarUrl(newAvatarUrl: String) {
        _avatarUrl.value = newAvatarUrl
    }
}