package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.Post
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
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
        _loading.value = true
        delay(1000)
        _posts.value = repository.getPosts()
        _loading.value = false
    }

    fun onSearch(query: String) {
        _query.value = query

        viewModelScope.launch {
            searchPosts(query)
        }
    }

    suspend fun searchPosts(query: String) {
        _loading.value = true
        delay(500)
        _posts.value = repository.searchPosts(query)
        _loading.value = false
    }
}
