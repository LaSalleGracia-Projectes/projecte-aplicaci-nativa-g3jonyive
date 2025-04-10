package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    val fullname = mutableStateOf("Toni Gimenez")
    val phoneNumber = mutableStateOf("612345678")
    val email = mutableStateOf("toni.gimenez@gracia.lasalle.cat")
    val photoUrl = mutableStateOf("https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50")

    init {
        auth.currentUser?.let { user ->
            fullname.value = user.displayName.orEmpty()
            phoneNumber.value = user.phoneNumber.orEmpty()
            email.value = user.email.orEmpty()
            photoUrl.value = user.photoURL.orEmpty()
        }
    }

    fun onLogout() {
        viewModelScope.launch { auth.signOut() }
    }
}
