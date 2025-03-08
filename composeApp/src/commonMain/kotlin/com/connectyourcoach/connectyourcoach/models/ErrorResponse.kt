package com.connectyourcoach.connectyourcoach.models

data class ErrorResponse(
    val error: String,
    val details: String,
    val exception: Exception
)
