package com.connectyourcoach.connectyourcoach.models

import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.DrawableResource

// Data class
data class ChatPreview(
    val id: String,
    val userName: String,
    val lastMessage: String,
    val time: String,
    val avatar: DrawableResource
)

fun ChatPreview.matches(query: String): Boolean {
    return userName.contains(query, ignoreCase = true) ||
            lastMessage.contains(query, ignoreCase = true)
}

val sampleChats = listOf(
    ChatPreview("1", "María López", "Nos vemos mañana!", "10:45 AM", Res.drawable.logo),
    ChatPreview("2", "Juan Pérez", "¿Qué tal todo?", "09:30 AM", Res.drawable.logo),
    ChatPreview("3", "Laura García", "Gracias! 😊", "20:50 PM", Res.drawable.logo),
    ChatPreview("4", "Andrés Torres", "Te llamo en un rato", "11:15 AM", Res.drawable.logo),
    ChatPreview("5", "Carmen Ruiz", "¿Has visto el correo?", "08:05 AM", Res.drawable.logo),
    ChatPreview("6", "Diego Fernández", "Perfecto, gracias!", "13:40 PM", Res.drawable.logo),
    ChatPreview("7", "Sofía Martínez", "Jajaja muy bueno 😂", "22:10 PM", Res.drawable.logo),
    ChatPreview("8", "Luis Ramírez", "Nos reunimos a las 3", "14:55 PM", Res.drawable.logo),
    ChatPreview("9", "Paula Sánchez", "Estoy llegando!", "17:25 PM", Res.drawable.logo),
    ChatPreview("10", "Jorge Castillo", "¿Puedes repetir eso?", "19:00 PM", Res.drawable.logo),
    ChatPreview("11", "Valentina Gómez", "Todo en orden ✨", "10:20 AM", Res.drawable.logo),
    ChatPreview("12", "Sebastián Morales", "Ok, entendido", "12:35 PM", Res.drawable.logo),
    ChatPreview("13", "Isabel Herrera", "Nos vemos luego!", "16:45 PM", Res.drawable.logo)
)

