package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.Post
import com.connectyourcoach.connectyourcoach.repositories.TablonRepository
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch

class AddCardViewModel : ViewModel() {
    private val tablonRepository = TablonRepository()
    private val userRepository = UserRepository()

    private val _price: MutableState<String> = mutableStateOf("")
    val price: MutableState<String> get() = _price

    private val _priceError: MutableState<String> = mutableStateOf("")
    val priceError: MutableState<String> get() = _priceError

    private val _title: MutableState<String> = mutableStateOf("")
    val title: MutableState<String> get() = _title

    private val _titleError: MutableState<String> = mutableStateOf("")
    val titleError: MutableState<String> get() = _titleError

    private val _description: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> get() = _description

    private val _descriptionError: MutableState<String> = mutableStateOf("")
    val descriptionError: MutableState<String> get() = _descriptionError

    private val _error: MutableState<String> = mutableStateOf("")
    val error: MutableState<String> get() = _error

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: MutableState<Boolean> get() = _isLoading

    fun updatePrice(newPrice: String) {
        _price.value = newPrice.replace(",", ".")
    }

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun resetErrors() {
        _priceError.value = ""
        _titleError.value = ""
        _descriptionError.value = ""
        _error.value = ""
    }

    fun validateFields(): Boolean {
        if (price.value.isEmpty()) {
            _priceError.value = "Price cannot be empty"
        } else if (price.value.toDoubleOrNull() != null) {
            _priceError.value = "Price must be a number"
        }
        if (title.value.isEmpty()) {
            _titleError.value = "Title cannot be empty"
        }
        if (description.value.isEmpty()) {
            _descriptionError.value = "Description cannot be empty"
        }

        return  price.value.isNotEmpty() &&
                price.value.toDoubleOrNull() != null &&
                title.value.isNotEmpty() &&
                description.value.isNotEmpty()
    }

    fun onClickAddPost(onSubmit: () -> Unit) {
        viewModelScope.launch {
            resetErrors()

            if (!validateFields()) {
                return@launch
            }

            addPost(onSubmit)
        }
    }

    private suspend fun addPost(onSubmit: () -> Unit) {
        _isLoading.value = true

        var user_id: Int = 0

        userRepository.getUserByNicknameOrUID(
            nickname = Firebase.auth.currentUser?.uid ?: "",
            onSuccessResponse = { user ->
                user_id = user.id!!
            },
            onErrorResponse = { error ->
                _isLoading.value = false
                _error.value = error.details
            }
        )

        val post = Post(
            price = price.value.toDouble(),
            title = title.value,
            description = description.value,
            user_id = user_id,
        )

        tablonRepository.createPost(
            post = post,
            token = Firebase.auth.currentUser?.getIdToken(false) ?: "",
            onSuccessResponse = { post ->
                onSubmit()
            },
            onErrorResponse = { error ->
                _error.value = error.details
            },
            onFinish = {
                _isLoading.value = false
            }
        )
    }
}