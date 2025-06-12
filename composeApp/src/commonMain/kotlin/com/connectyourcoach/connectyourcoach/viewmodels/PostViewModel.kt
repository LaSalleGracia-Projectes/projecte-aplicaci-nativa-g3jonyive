package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.Post
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import kotlinx.coroutines.launch

class PostViewModel(postId: String) : ViewModel() {
    val tablonRepository = TablonRepository()
    val userRepository = UserRepository()

    private val _post = mutableStateOf<Post?>(null)
    val post get() = _post

    private val _user = mutableStateOf<User?>(null)
    val user get() = _user

    init {
        viewModelScope.launch {
            loadPost(postId)
        }
    }

    suspend fun loadUser(userId: Int?) {
        userRepository.getUserById(
            id = userId ?: 0,
            onSuccessResponse = { user ->
                _user.value = user
            },
            onErrorResponse = { error ->
                //TODO Handle error
                println("Error loading user: ${error.details}")
            }
        )
    }

    suspend fun loadPost(postId: String) {
        tablonRepository.getPostById(
            id = postId,
            onSuccessResponse = { post ->
                _post.value = post
                viewModelScope.launch {
                    loadUser(_post.value?.user_id)
                }
            },
            onErrorResponse = { error ->
                //TODO Handle error
                println("Error loading post: ${error.details}")
            }
        )
    }
}