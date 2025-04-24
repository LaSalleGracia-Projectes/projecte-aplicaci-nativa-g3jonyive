package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.connectyourcoach.connectyourcoach.models.ChatModel
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.DrawableResource

class ChatViewModel : ViewModel() {

    private val _messages = mutableStateListOf(
        ChatModel("1", "Hola! ¿Cómo estás?", "Usuario1", getUserAvatar("Usuario1")),
        ChatModel("2", "Todo bien, ¿y tú?", "Usuario2", getUserAvatar("Usuario2")),
        ChatModel("3", "También bien, gracias!", "Usuario1", getUserAvatar("Usuario1"))
    )
    val messages: List<ChatModel> get() = _messages

    var currentMessage by mutableStateOf("")
        private set

    fun onMessageChange(newMessage: String) {
        currentMessage = newMessage
    }

    fun sendMessage() {
        if (currentMessage.isNotBlank()) {
            val newMessage = ChatModel(
                id = (_messages.size + 1).toString(),
                text = currentMessage,
                userId = "Tú",
                avatar = getUserAvatar("Tú")
            )
            _messages.add(newMessage)
            currentMessage = ""
        }
    }

    private fun getUserAvatar(userId: String): DrawableResource {
        return Res.drawable.logo
    }
}
