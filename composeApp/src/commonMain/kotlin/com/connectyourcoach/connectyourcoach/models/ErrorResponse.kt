package com.connectyourcoach.connectyourcoach.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String,
    val details: String,
    val exception: CustomException
)
