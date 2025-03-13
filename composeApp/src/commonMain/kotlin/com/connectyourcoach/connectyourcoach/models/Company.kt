package com.connectyourcoach.connectyourcoach.models

data class Company(
    val id: String,
    val created_at: String,
    val updated_at: String,

    val name: String,
    val logo: String,
    val description: String,
)
