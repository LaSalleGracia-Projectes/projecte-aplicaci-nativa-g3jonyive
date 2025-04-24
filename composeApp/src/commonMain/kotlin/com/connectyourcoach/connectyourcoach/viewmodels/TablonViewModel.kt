package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.Post
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TablonViewModel : ViewModel() {
    private val repository = TablonRepository()

    private val _posts: MutableState<List<Post>> = mutableStateOf(emptyList())
    val posts: MutableState<List<Post>> get() = _posts

    private val _query: MutableState<String> = mutableStateOf("")
    val query: MutableState<String> get() = _query

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> get() = _loading

    init {
        viewModelScope.launch {
            loadPosts()
        }
    }

    suspend fun loadPosts() {
        repository.getPosts(
            onSuccessResponse = { posts ->
                _posts.value = posts
                _loading.value = false
            },
            onErrorResponse = { error ->
                //TODO Handle error
                _posts.value = emptyList()
                println("Error loading posts: ${error.details}")
                _loading.value = false
            }
        )
        _loading.value = true
    }

    fun onSearch(query: String) {
        _query.value = query

        viewModelScope.launch {
            repository.searchPosts(
                query = query,
                onSuccessResponse = { posts ->
                    _posts.value = posts
                    _loading.value = false
                },
                onErrorResponse = { error ->
                    //TODO Handle error
                    _posts.value = emptyList()
                    println("Error loading posts: ${error.details}")
                    _loading.value = false
                }
            )
        }
    }
}
