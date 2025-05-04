package com.connectyourcoach.connectyourcoach.models

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class FirestoreChat(
    val id: String = "",
    val created: Timestamp = Timestamp.now(),
    val messages: List<FirestoreChatMessage> = emptyList(),
    val participants: List<String> = emptyList(),
)
