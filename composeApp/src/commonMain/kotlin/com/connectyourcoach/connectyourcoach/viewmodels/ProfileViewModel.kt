package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    val fullname = mutableStateOf("")
    val phoneNumber = mutableStateOf("")
    val email = mutableStateOf("")
    val photoUrl = mutableStateOf("")

    init {
        loadUserData()
    }

    private fun loadUserData() {
        auth.currentUser?.let { user ->
            fullname.value = user.displayName ?: ""
            phoneNumber.value = user.phoneNumber ?: ""
            email.value = user.email ?: ""
            photoUrl.value = user.photoURL ?: "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50"
        }
    }

    fun refreshUserData() {
        viewModelScope.launch {
            auth.currentUser?.reload()
            loadUserData()
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            auth.signOut()
            // Reset values after logout
            fullname.value = ""
            phoneNumber.value = ""
            email.value = ""
            photoUrl.value = ""
        }
    }
}