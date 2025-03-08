package com.connectyourcoach.connectyourcoach.models

data class PaymentMethod(
    val id: String,
    val created_at: String,
    val updated_at: String,

    val name: String,
    val description: String
)
