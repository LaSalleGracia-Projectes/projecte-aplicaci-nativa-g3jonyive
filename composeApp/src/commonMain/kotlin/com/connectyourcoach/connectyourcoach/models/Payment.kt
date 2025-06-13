package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val id: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null,

    val user1_id: Int? = null,
    val user2_id: Int,
    val post_id: Int,
    val amount: Float,
    val payment_date: String? = null,
    val payment_method_id: Int
)
