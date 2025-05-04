package com.connectyourcoach.connectyourcoach.repositories

import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore

class FirestoreUserRepository {
    private val firestore = Firebase.firestore

    suspend fun addUser(user: FirestoreUser) {
        firestore.collection("users")
            .document(user.uid)
            .set(user)
    }

    suspend fun deleteUser(userId: String) {
        firestore.collection("users")
            .document(userId)
            .delete()
    }

    suspend fun updateUser(user: FirestoreUser) {
        firestore.collection("users")
            .document(user.uid)
            .set(user)
    }
}