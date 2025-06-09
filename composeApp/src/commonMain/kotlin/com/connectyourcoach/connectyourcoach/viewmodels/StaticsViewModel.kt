package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class StaticsViewModel : ViewModel() {
    private val _users = mutableListOf<User>()
    val users: List<User> get() = _users

    var error: String? by mutableStateOf(null)
        private set

    private val repository = UserRepository()
    private val tablonRepository = TablonRepository()

    var userPostCounts by mutableStateOf<List<Pair<String, Int>>>(emptyList())
    var postCountError by mutableStateOf<String?>(null)

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
                    error = "Error while charging statics"
                }
            )
        }
    }

    fun loadUserPostCounts() {
        if (users.isEmpty()) return

        viewModelScope.launch {
            try {
                val results = users.map { user ->
                    async {
                        // Aquí s'assumeix que getPostsByUserIdSuspend és una funció suspend que retorna la llista de posts
                        val posts = tablonRepository.getPostsByUserIdSuspend(user.id.toString())
                        Pair(user.username ?: "—", posts.size)
                    }
                }.awaitAll()

                userPostCounts = results.sortedByDescending { it.second }
                postCountError = null
            } catch (e: Exception) {
                postCountError = "Error loading posts: ${e.message}"
            }
        }
    }
}
