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

    init {
        viewModelScope.launch {
            loadUserData()
        }
    }

    private suspend fun loadUserData() {
        repository.getUserByNicknameOrUID(
            nickname = auth.currentUser?.uid ?: "",
            onSuccessResponse = { user ->
                _user.value = user
            },
            onErrorResponse = { error ->
                // Handle error
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