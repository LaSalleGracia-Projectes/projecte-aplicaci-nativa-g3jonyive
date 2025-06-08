package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ControlPanelViewModel : ViewModel() {
    private val _users = mutableStateListOf<User>()
    val users: List<User> get() = _users

    var error: String? by mutableStateOf(null)
        private set

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            try {
                // Exemple fake, posa aquí la teva crida real
                delay(1000)
                _users.addAll(listOf(User(
                    "Anna",
                    created_at = TODO(),
                    email = TODO(),
                    full_name = TODO(),
                    id = TODO(),
                    phone = TODO(),
                    profile_picture = TODO(),
                    uid = TODO(),
                    updated_at = TODO(),
                    username = TODO(),
                    active = true
                ), User(
                    "Marc",
                    created_at = TODO(),
                    email = TODO(),
                    full_name = TODO(),
                    id = TODO(),
                    phone = TODO(),
                    profile_picture = TODO(),
                    uid = TODO(),
                    updated_at = TODO(),
                    username = TODO(),
                    active = TODO()
                )))
            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}
