package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.models.FirestoreChatMessage
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldValue
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class FirestoreChatRepository {
    private val firestore = Firebase.firestore
    private val collection = firestore.collection("chats")

    suspend fun addChat(chatId: String, chat: FirestoreChat) {
        collection
            .document(chatId)
            .set(chat)
    }

    suspend fun deleteChat(chatId: String) {
        collection
            .document(chatId)
            .delete()
    }

    suspend fun updateChat(chatId: String, chat: FirestoreChat) {
        collection
            .document(chatId)
            .set(chat)
    }

    fun getChats() = flow {
        collection
            .snapshots.collect { querySnapshot ->
                val chats = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<FirestoreChat>()
                }
            emit(chats)
        }
    }

    fun getChatById(chatId: String) = flow {
        collection
            .document(chatId)
            .snapshots.collect { documentSnapshot ->
                val chat = documentSnapshot.data<FirestoreChat>()
                emit(chat)
            }
    }

    fun getChatByUserId(userId: String) = flow {
        collection
            .snapshots.collect { querySnapshot ->
                val chats = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    val chat = documentSnapshot.data<FirestoreChat>()
                    if (chat.participants.contains(userId)) {
                        chat
                    } else {
                        null
                    }
                }
                emit(chats)
            }
    }

    suspend fun addMessageToChat(chatId: String, message: FirestoreChatMessage) {
        collection
            .document(chatId)
            .update(
                data = mapOf(
                    "messages" to FieldValue.arrayUnion(message)
                )
            )
    }
}