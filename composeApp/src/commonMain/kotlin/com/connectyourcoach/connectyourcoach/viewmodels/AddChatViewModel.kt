package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.repositories.FirestoreChatRepository
import com.connectyourcoach.connectyourcoach.repositories.FirestoreUserRepository
import com.connectyourcoach.connectyourcoach.repositories.UserRepository
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.models.FirestoreChatMessage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class AddChatViewModel : ViewModel() {
    private val firebaseUserRepository = FirestoreUserRepository()
    private val firebaseChatRepository = FirestoreChatRepository()
    private val userRepository = UserRepository()

    private val _phone: MutableState<String> = mutableStateOf("")
    val phone: MutableState<String> get() = _phone

    private val _users: MutableState<List<User>> = mutableStateOf(emptyList())
    val users: MutableState<List<User>> get() = _users

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: MutableState<Boolean> get() = _loading

    fun onSearchUser(newPhone: String) {
        _phone.value = newPhone

        viewModelScope.launch {
            searchUser()
        }
    }

    private suspend fun searchUser() {
        if (phone.value.isEmpty()) {
            return
        }

        _loading.value = true

        userRepository.getUsers(
            onSuccessResponse = { users ->
                _users.value = users.filter { user ->
                    user.phone?.contains(phone.value) == true && user.uid != Firebase.auth.currentUser?.uid
                }
            },
            onErrorResponse = {

            },
            onFinish = {
                _loading.value = false
            }
        )
    }

    fun onSelectUser(user: User) {
        viewModelScope.launch {
            //TODO Falta verificar que el usuario no tenga un chat abierto

            val message = FirestoreChatMessage(
                sender = Firebase.auth.currentUser?.uid ?: generateRandomID(),
                message = "Hello ${user.username}, I would like to connect with you.",
            )

            val chat = FirestoreChat(
                id = generateRandomID(),
                participants = listOf(
                    Firebase.auth.currentUser?.uid ?: generateRandomID(),
                    user.uid
                ),
                messages = listOf(message)
            )

            firebaseChatRepository.addChat(
                chat = chat
            )
        }
    }

    fun generateRandomID(): String {
        return Clock.System.now().toEpochMilliseconds().toString()
    }

}