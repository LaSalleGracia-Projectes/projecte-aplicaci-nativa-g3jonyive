package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class ChatMessage(val id: String, val text: String, val userId: String, val avatar: DrawableResource)

// Simulación de imágenes de usuario
fun getUserAvatar(userId: String): DrawableResource {
    return if (userId == "Tú") Res.drawable.logo else Res.drawable.logo
}

@Composable
fun ChatView(paddingValues: PaddingValues) {
    var messages by remember { mutableStateOf(listOf(
        ChatMessage("1", "Hola! ¿Cómo estás?", "Usuario1", getUserAvatar("Usuario1")),
        ChatMessage("2", "Todo bien, ¿y tú?", "Usuario2", getUserAvatar("Usuario2")),
        ChatMessage("3", "También bien, gracias!", "Usuario1", getUserAvatar("Usuario1"))
    )) }

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                ChatBubble(message)
            }
        }

        // Input de mensaje
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Escribe un mensaje...") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color(0xFF173040),
                    unfocusedBorderColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (text.text.isNotBlank()) {
                        messages = messages + ChatMessage(
                            (messages.size + 1).toString(),
                            text.text,
                            "Tú",
                            getUserAvatar("Tú")
                        )
                        text = TextFieldValue("")
                    }
                }
            ) {
                Icon(Icons.Filled.Send, contentDescription = "Enviar", tint = Color(0xFF173040))
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val isCurrentUser = message.userId == "Tú"
    val bubbleColor = if (isCurrentUser) Color(0xFF5FD9AC) else Color(0xFFD9D8D2)
    val textColor = if (isCurrentUser) Color.White else Color.Black
    val alignment = if (isCurrentUser) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isCurrentUser) {
            AvatarIcon(message.avatar)
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(bubbleColor)
                .padding(12.dp)
        ) {
            Text(text = message.text, fontSize = 16.sp, color = textColor)
        }

        if (isCurrentUser) {
            Spacer(modifier = Modifier.width(16.dp))
            AvatarIcon(message.avatar)
        }
    }
}

@Composable
fun AvatarIcon(imageRes: DrawableResource) {
    Icon(
        painter = painterResource(
            resource = imageRes
        ),
        contentDescription = "Avatar",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    )
}
