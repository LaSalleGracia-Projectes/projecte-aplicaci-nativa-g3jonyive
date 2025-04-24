package com.connectyourcoach.connectyourcoach.models

import org.jetbrains.compose.resources.DrawableResource

data class ChatModel(
    val id: String,
    val text: String,
    val userId: String,
    val avatar: DrawableResource
)
