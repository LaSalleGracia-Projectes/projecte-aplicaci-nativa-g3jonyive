package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var birth_date: String? = null,
    val created_at: String? = null,
    val email: String,
    var full_name: String,
    val id: Int? = null,
    var phone: String? = null,
    var profile_picture: String? = null,
    val uid: String,
    val updated_at: String? = null,
    var username: String,
    var active: Boolean
)