package com.connectyourcoach.connectyourcoach.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import com.connectyourcoach.connectyourcoach.repositories.FirestoreChatRepository
import com.connectyourcoach.connectyourcoach.repositories.FirestoreUserRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow

class ListChatViewModel : ViewModel() {
    private val firestoreUserRepository = FirestoreUserRepository()
    private val firestoreChatRepository = FirestoreChatRepository()

    val chats = firestoreChatRepository.getChats()

    fun getUser(chat: FirestoreChat): Flow<FirestoreUser> {
        val uid = Firebase.auth.currentUser?.uid ?: ""
        val userID = chat.participants.find { it != uid }
        return firestoreUserRepository.getUserById(userID ?: "")
    }
}