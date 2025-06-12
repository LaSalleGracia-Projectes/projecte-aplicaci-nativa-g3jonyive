package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.Post
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class PostViewModel(postId: String) : ViewModel() {
    val tablonRepository = TablonRepository()
    val userRepository = UserRepository()

    private val auth by mutableStateOf(Firebase.auth)

    private val _post = mutableStateOf<Post?>(null)
    val post get() = _post

    private val _user = mutableStateOf<User?>(null)
    val user get() = _user

    private val _isLiked = mutableStateOf(false)
    val isLiked get() = _isLiked

    private val _likes = mutableStateOf<Int>(0)
    val likes get() = _likes

    init {
        viewModelScope.launch {
            loadPost(postId)
        }
    }

    fun onLike() {
        viewModelScope.launch {
            toggleLike()
        }
    }

    suspend fun toggleLike() {
        tablonRepository.likePost(
            id = _post.value?.id.toString(),
            token = auth.currentUser?.getIdToken(false) ?: "",
            onSuccessResponse = { like ->
                _isLiked.value = like
                _likes.value += if (like) 1 else -1
            },
            onErrorResponse = { error ->
                //TODO Handle error
                println("Error liking post: ${error.details}")
            }
        )
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
        tablonRepository.getPostLikes(
            id = postId,
            onSuccessResponse = { likes ->
                _likes.value = likes.trim().toIntOrNull() ?: 0
            },
            onErrorResponse = { error ->
                //TODO Handle error
                println("Error getting likes: ${error.details}")
            }
        )
    }
}