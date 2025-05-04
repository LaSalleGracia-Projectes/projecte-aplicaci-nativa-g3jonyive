package com.connectyourcoach.connectyourcoach.models

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class FirestoreChatMessage(
    val message: String,
    val sender: String,
    val created: Timestamp = Timestamp.now(),
)
