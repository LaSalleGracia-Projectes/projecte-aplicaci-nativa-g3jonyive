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

class ControlPanelViewModel : ViewModel() {
    private val _users = mutableStateListOf<User>()
    val users: List<User> get() = _users

    var error: String? by mutableStateOf(null)
        private set

    private val repository = UserRepository()
    private val dummyToken = "TOKEN" // Canvia aquí pel token real

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
                onErrorResponse = { errorResponse ->
                    error = "Error al carregar usuaris"
                }
            )
        }
    }

    fun toggleUserActiveStatus(user: User) {
        val updatedUser = user.copy(active = !user.active)
        viewModelScope.launch {
            repository.updateUser(
                user = updatedUser,
                token = dummyToken,
                onSuccessResponse = { updated ->
                    val index = _users.indexOfFirst { it.username == updated.username }
                    if (index != -1) {
                        _users[index] = updated
                        error = null
                    }
                },
                onErrorResponse = { errorResponse ->
                    error = "No s'ha pogut actualitzar l'usuari"
                    loadUsers() // Recarrega per mantenir coherència
                }
            )
        }
    }
}
