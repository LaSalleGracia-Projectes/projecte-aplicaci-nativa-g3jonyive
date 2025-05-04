package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import com.connectyourcoach.connectyourcoach.repositories.FirestoreChatRepository
import com.connectyourcoach.connectyourcoach.repositories.FirestoreUserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatId: String,
) : ViewModel() {
    val firestoreUserRepository = FirestoreUserRepository()
    val firestoreChatRepository = FirestoreChatRepository()

    val chat = firestoreChatRepository.getChatById(chatId)
    val user = chat.map { chat ->
        chat.let {
            firestoreUserRepository.getUserById(it.participants.first { member -> member != Firebase.auth.currentUser?.uid }).last()
        }
    }

    private val _message: MutableState<String> = mutableStateOf("")
    val message: MutableState<String> get() = _message

    fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }

    fun sendMessage() {
        viewModelScope.launch {
            onSendMessage()
        }
    }

    suspend fun onSendMessage() {
        /* val currentUser = Firebase.auth.currentUser?.uid
        if (currentUser != null) {
            firestoreChatRepository.addMessageToChat(
                chatId = chatId, message = message.value
            )
        } */
    }
}