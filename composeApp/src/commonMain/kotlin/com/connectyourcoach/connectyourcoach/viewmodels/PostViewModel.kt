package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.Post
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository
import kotlinx.coroutines.launch

class PostViewModel(postId: String) : ViewModel() {
    val repository = TablonRepository()

    private val _post = mutableStateOf<Post?>(null)
    val post get() = _post

    init {
        viewModelScope.launch {
            loadPost(postId)
        }
    }

    suspend fun loadPost(postId: String) {
        repository.getPostById(
            id = postId,
            onSuccessResponse = { post ->
                _post.value = post
            },
            onErrorResponse = { error ->
                //TODO Handle error
                println("Error loading post: ${error.details}")
            }
        )
    }
}