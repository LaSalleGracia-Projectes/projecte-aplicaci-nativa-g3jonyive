package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.apicamera.ImageLoader
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.FirestoreUserRepository
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    private val _full_name: MutableState<String> = mutableStateOf("")
    val full_name: State<String> get() = _full_name

    private val _phone: MutableState<String> = mutableStateOf("")
    val phone: State<String> get() = _phone

    private val _photoUrl: MutableState<String> = mutableStateOf("")
    val photoUrl: State<String> get() = _photoUrl

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: State<Boolean> get() = _loading

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> get() = _error

    private val _saved: MutableState<Boolean> = mutableStateOf(false)
    val saved: State<Boolean> get() = _saved

    private val userRepository = UserRepository()
    private val firebaseUserRepository = FirestoreUserRepository()

    private val imageLoader = ImageLoader(HttpClient())

    init {
        viewModelScope.launch {
            auth.currentUser?.let { user ->
                userRepository.getUserByNicknameOrUID(
                    nickname = user.uid,
                    onSuccessResponse = { fetchedUser ->
                        _full_name.value = fetchedUser.full_name
                        _phone.value = fetchedUser.phone ?: ""
                        _photoUrl.value = fetchedUser.profile_picture ?: ""
                    },
                    onErrorResponse = { error ->
                        _error.value = error.details
                    },
                )
            }
        }
    }

    fun onFullNameChange(newFullName: String) {
        _full_name.value = newFullName
    }

    fun isValidFullName(fullName: String): Boolean {
        return fullName.isNotBlank()
    }

    fun onPhoneChange(newPhone: String) {
        _phone.value = newPhone
    }

    fun isValidPhone(): Boolean = _phone.value.length == 9 && _phone.value.all { it.isDigit() }

    fun isValidSave(): Boolean =
        isValidFullName(_full_name.value) &&
        (isValidPhone() || _phone.value.isBlank())

    fun onGenerateNewAvatar() {
        viewModelScope.launch { generateNewAvatar() }
    }

    suspend fun generateNewAvatar() {
        _photoUrl.value = imageLoader.getRandomAvatar()
    }

    fun onUploadImage() {
        viewModelScope.launch { uploadImage() }
    }

    suspend fun uploadImage() {

    }

    fun onSave() {
        viewModelScope.launch { save() }
    }

    suspend fun save() {
        _saved.value = true
    }
}