package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    private val repository = UserRepository()

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    private val _active = mutableStateOf(true)
    val active: State<Boolean> get() = _active

    init {
        viewModelScope.launch {
            loadUserData()
        }
    }

    private suspend fun loadUserData() {
        _isLoading.value = true
        repository.getUserByNicknameOrUID(
            nickname = auth.currentUser?.uid ?: "",
            onSuccessResponse = { user ->
                _user.value = user
            },
            onErrorResponse = { error ->

            },
            onFinish = {
                _isLoading.value = false
            }
        )
    }

    fun onClickLogout(onLogout: () -> Unit) {
        viewModelScope.launch {
            auth.signOut()
            onLogout()
        }
    }
}