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

    // Estats del formulari
    private val _fullName: MutableState<String> = mutableStateOf("")
    val fullname: MutableState<String> get() = _fullName

    private val _email: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> get() = _email

    private val _phone: MutableState<String> = mutableStateOf("")
    val phone: MutableState<String> get() = _phone

    private val _password: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> get() = _password

    // Estats de la UI
    private val _saveError: MutableState<String?> = mutableStateOf(null)
    val saveError: MutableState<String?> get() = _saveError

    private val _saved: MutableState<Boolean> = mutableStateOf(false)
    val saved: MutableState<Boolean> get() = _saved

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> get() = _loading

    private val _settingsError: MutableState<String> = mutableStateOf("")
    val settingsError: State<String> get() = _settingsError

    // Gestió d'avatar
    private val _photoUrl: MutableState<String> = mutableStateOf("")
    val photoUrl: State<String> get() = _photoUrl

    private val _showAvatarGenerator: MutableState<Boolean> = mutableStateOf(false)
    val showAvatarGenerator: State<Boolean> get() = _showAvatarGenerator

    private lateinit var imageLoader: ImageLoader

    init {
        auth.currentUser?.let { user ->
            user.displayName?.let { _fullName.value = it }
            user.email?.let { _email.value = it }
            user.phoneNumber?.let { _phone.value = it }
            user.photoURL?.let { _photoUrl.value = it }
        }
    }

    fun initialize(httpClient: HttpClient) {
        imageLoader = ImageLoader(httpClient)
    }

    // Actualitzacions dels camps
    fun updateFullname(fullname: String) {
        _fullName.value = fullname
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePhone(phone: String) {
        _phone.value = phone
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    // Gestió d'avatar
    fun updateAvatarUrl(newAvatarUrl: String) {
        _photoUrl.value = newAvatarUrl
    }

    fun generateRandomAvatar() {
        viewModelScope.launch {
            try {
                _photoUrl.value = imageLoader.getRandomAvatar()
            } catch (e: Exception) {
                _settingsError.value = "Error en generar l'avatar: ${e.message}"
            }
        }
    }

    fun showAvatarGenerator(show: Boolean) {
        _showAvatarGenerator.value = show
    }

    // Validacions
    fun isValidEmail(): Boolean = _email.value.contains("@") && _email.value.contains(".")

    fun isValidPhone(): Boolean {
        if (_phone.value.isEmpty()) return true
        return _phone.value.length >= 9 && _phone.value.all { it.isDigit() }
    }

    fun isValidPassword(): Boolean {
        if (_password.value.isEmpty()) return true
        return _password.value.length >= 8 &&
                _password.value.any { it.isUpperCase() } &&
                _password.value.any { it.isLowerCase() } &&
                _password.value.any { it.isDigit() } &&
                _password.value.any { "!@#\$%^&*()_+{}[]:;<>,.?/~`".contains(it) }
    }

    fun isValidSave(): Boolean = isValidEmail() && isValidPhone() &&
            (_password.value.isEmpty() || isValidPassword())

    // Operacions
    fun onSave() {
        viewModelScope.launch { save() }
    }

    suspend fun save() {
        try {
            _loading.value = true
            auth.currentUser?.let { user ->
                // Actualitzar nom i foto
                user.updateProfile(
                    displayName = _fullName.value,
                    photoUrl = _photoUrl.value.ifEmpty { user.photoURL }
                )

                // Actualitzar email si ha canviat
                if (_email.value != user.email) {
                    user.updateEmail(_email.value)
                }

                // Actualitzar contrasenya si s'ha introduït
                if (_password.value.isNotEmpty()) {
                    user.updatePassword(_password.value)
                }

            }
            _saved.value = true
        } catch (e: Exception) {
            _saveError.value = e.message ?: "Error desconegut"
            _saved.value = false
        } finally {
            _loading.value = false
        }
    }
}