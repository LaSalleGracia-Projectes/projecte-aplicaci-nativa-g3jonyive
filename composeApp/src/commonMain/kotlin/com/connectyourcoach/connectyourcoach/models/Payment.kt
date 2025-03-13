package com.connectyourcoach.connectyourcoach.models

data class Payment(
    val id: String,
    val created_at: String,
    val updated_at: String,

    val user1_id: Int,
    val user2_id: Int,
    val post_id: Int,
    val amount: Float,
    val payment_date: String,
    val payment_method_id: Int
)
