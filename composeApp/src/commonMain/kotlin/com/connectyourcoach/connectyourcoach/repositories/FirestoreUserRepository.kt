package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.flow

class FirestoreUserRepository {
    private val firestore = Firebase.firestore
    private val collection = firestore.collection("users")

    suspend fun addUser(user: FirestoreUser) {
        collection
            .document(user.uid)
            .set(user)
    }

    suspend fun deleteUser(userId: String) {
        collection
            .document(userId)
            .delete()
    }

    suspend fun updateUser(user: FirestoreUser) {
        collection
            .document(user.uid)
            .set(user)
    }

    fun getUserById(userId: String) = flow {
        collection
            .document(userId)
            .snapshots.collect { documentSnapshot ->
                val user = documentSnapshot.data<FirestoreUser?>()
                emit(user)
            }
    }
}