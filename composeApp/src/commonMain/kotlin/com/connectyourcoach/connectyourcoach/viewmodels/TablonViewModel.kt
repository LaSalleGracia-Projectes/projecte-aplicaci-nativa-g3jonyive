package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class TablonViewModel : ViewModel() {
    val auth: MutableState<FirebaseAuth> = mutableStateOf(Firebase.auth)

    fun signOut() {
        viewModelScope.launch {
            auth.value.signOut()
        }
    }
}
