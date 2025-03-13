package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val auth by mutableStateOf(Firebase.auth)

    private val _fullName: MutableState<String> = mutableStateOf("Toni Gimenez")
    val fullname: MutableState<String> get() = _fullName

    private val _phoneNumber: MutableState<String> = mutableStateOf("612345678")
    val phoneNumber: MutableState<String> get() = _phoneNumber

    private val _email: MutableState<String> = mutableStateOf("toni.gimenez@gracia.lasalle.cat")
    val email: MutableState<String> get() = _email

    init {
        if (auth.currentUser != null) {
            if (!auth.currentUser?.displayName.isNullOrEmpty()) {
                _fullName.value = auth.currentUser!!.displayName!!
            }
            if (!auth.currentUser?.phoneNumber.isNullOrEmpty()) {
                _phoneNumber.value = auth.currentUser!!.phoneNumber!!
            }
            if (!auth.currentUser?.email.isNullOrEmpty()) {
                _email.value = auth.currentUser!!.email!!
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            auth.signOut()
        }
    }
}