package com.connectyourcoach.connectyourcoach.models

import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable

@Serializable
data class FirestoreChat(
    val created: DateTimeUnit,
    val messages: List<FirestoreChatMessage> = emptyList(),
    val participants: List<String> = emptyList(),
)
