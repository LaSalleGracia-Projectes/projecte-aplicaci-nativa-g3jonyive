package com.connectyourcoach.connectyourcoach.models

data class User(
    val id: String,
    val created_at: String,
    val updated_at: String,

    val uid: String,
    val username: String,
    val email: String,
    val full_name: String,
    val phone: String? = null,
    val birth_date: String,
    val profile_picture: String? = null
)
