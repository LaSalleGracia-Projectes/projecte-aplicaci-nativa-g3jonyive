package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.*
import com.connectyourcoach.connectyourcoach.models.ChatPreview
import com.connectyourcoach.connectyourcoach.models.sampleChats
import com.connectyourcoach.connectyourcoach.models.matches

class ListChatViewModel {
    var searchText by mutableStateOf("")
    private val originalChats = sampleChats
    var chatsActivos = mutableStateListOf<ChatPreview>().apply { addAll(originalChats) }
    var chatsArchivados = mutableStateListOf<ChatPreview>()

    val filteredChats: List<ChatPreview>
        get() = chatsActivos.filter { it.matches(searchText) }

    fun archiveChat(chat: ChatPreview) {
        chatsActivos.remove(chat)
        chatsArchivados.add(chat)
    }
}
