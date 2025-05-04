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

    suspend fun addChat(chat: FirestoreChat) {
        collection
            .document(chat.id)
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
                val chats = try {
                    querySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<FirestoreChat>()
                    }
                } catch (e: Exception) {
                    emptyList<FirestoreChat>()
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
                val allChats = try {
                    querySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<FirestoreChat>()
                    }
                } catch (e: Exception) {
                    emptyList<FirestoreChat>()
                }

                val chats: List<FirestoreChat> = allChats.filter { chat ->
                    chat.participants.contains(userId)
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