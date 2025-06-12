package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
data class Like(
    val id: Int,
    val created_at: String,
    val updated_at: String,

    val user_id: Int,
    val post_id: Int
)
