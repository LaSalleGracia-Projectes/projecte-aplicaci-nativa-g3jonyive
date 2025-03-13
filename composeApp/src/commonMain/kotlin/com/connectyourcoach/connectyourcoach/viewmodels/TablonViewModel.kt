package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.Post
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class TablonViewModel : ViewModel() {
    private val _posts: MutableState<List<Post>> = mutableStateOf(emptyList())
    val posts: MutableState<List<Post>> get() = _posts

    private val _query: MutableState<String> = mutableStateOf("")
    val query: MutableState<String> get() = _query

    init {
        for (i in 0..10) {
            _posts.value += Post(
                id = "$i",
                title = "Title $i",
                description = "Description $i",
                user_id = i,
                created_at = "Date $i",
                price = i * 100f,
                updated_at = "Date $i",
                photo = "https://sillaoficina365.es/modules/dbblog/views/img/post/147-espacio-oficina-por-persona.webp"
            )
        }
    }

    fun onSearch(query: String) {
        _query.value = query
    }
}
