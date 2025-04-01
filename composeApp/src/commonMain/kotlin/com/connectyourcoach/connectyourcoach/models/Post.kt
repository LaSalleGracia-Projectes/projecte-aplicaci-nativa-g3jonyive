package com.connectyourcoach.connectyourcoach.models


data class Post(
    val id: String,
    val created_at: String,
    val updated_at: String,

    val price: Float,
    val user_id: Int,
    val title: String,
    val description: String,
    val photo: String? = null,
    val company_id: Int? = null,
    val specialization_id: Int? = null,
)
