package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.connectyourcoach.connectyourcoach.models.Post
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository

class PostViewModel(postId: String) : ViewModel() {
    val repository = TablonRepository()

    private val _post = mutableStateOf<Post?>(null)
    val post get() = _post

    init {
        loadPost(postId)
    }

    fun loadPost(postId: String) {
        _post.value = repository.getPostById(postId)
    }
}