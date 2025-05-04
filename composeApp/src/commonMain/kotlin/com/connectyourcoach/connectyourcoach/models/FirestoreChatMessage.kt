package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
data class FirestoreChatMessage(
    val message: String,
    val sender: String,
)
