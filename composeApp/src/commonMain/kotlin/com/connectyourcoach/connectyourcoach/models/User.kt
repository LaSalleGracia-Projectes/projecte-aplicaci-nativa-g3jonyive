package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val birth_date: String? = null,
    val created_at: String,
    val email: String,
    val full_name: String,
    val id: Int,
    val phone: String? = null,
    val profile_picture: String? = null,
    val uid: String,
    val updated_at: String,
    val username: String
)