package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import kotlinx.coroutines.launch

class StaticsViewModel : ViewModel() {
    private val _users = mutableStateListOf<User>()
    val users: List<User> get() = _users

    var error: String? by mutableStateOf(null)
        private set

    private val repository = UserRepository()
    private val dummyToken = "TOKEN" // Substitueix pel token real

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            repository.getUsers(
                onSuccessResponse = { userList ->
                    _users.clear()
                    _users.addAll(userList)
                    error = null
                },
                onErrorResponse = {
                    error = "Error al carregar les dades d'estadística"
                }
            )
        }
    }
}
