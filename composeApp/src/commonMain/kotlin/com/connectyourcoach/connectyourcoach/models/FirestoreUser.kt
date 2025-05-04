package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
data class FirestoreUser (
    val uid: String,
    val username: String,
    val phone: String,
    val tokens: List<String> = emptyList(),
)