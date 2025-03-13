package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    private val _fullName by mutableStateOf("Toni Gimenez")
    val fullName get() = _fullName

    private val _phoneNumber by mutableStateOf("666666666")
    val phoneNumber get() = _phoneNumber

    private val _email by mutableStateOf(auth.currentUser?.email ?: "toni.gimenez@gmail.com")
    val email get() = _email

    fun onLogout() {
        viewModelScope.launch {
            auth.signOut()
        }
    }
}