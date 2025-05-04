package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable


@Serializable
data class Post(
    val id: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null,

    val price: Double,
    val user_id: Int,
    var title: String,
    var description: String,
    var photo: String? = null,
    var company_id: Int? = null,
    var specialization_id: Int? = null,
)
