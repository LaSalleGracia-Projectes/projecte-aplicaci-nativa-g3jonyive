package com.connectyourcoach.connectyourcoach.models

data class Comment(
    val id: String,
    val created_at: String,
    val updated_at: String,

    val user_id: Int,
    val post_id: Int,
    val comment: String
)
