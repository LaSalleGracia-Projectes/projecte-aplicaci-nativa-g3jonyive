package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.box
import connectyourcoach.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class ChatPreview(
    val id: String,
    val userName: String,
    val lastMessage: String,
    val time: String,
    val avatar: DrawableResource
)

@Composable
fun ListChatView(paddingValues: PaddingValues, onChatSelected: (ChatPreview) -> Unit) {
    val originalChats = remember {
        listOf(
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
    }

    var searchText by remember { mutableStateOf("") }
    val filteredChats = originalChats.filter {
        it.userName.contains(searchText, ignoreCase = true) ||
                it.lastMessage.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn {
            items(filteredChats) { chat ->
                ChatListItem(chat) { onChatSelected(chat) }
            }
        }
    }
}

@Composable
fun ChatListItem(chat: ChatPreview, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(resource = chat.avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(chat.userName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(chat.lastMessage, fontSize = 14.sp, color = Color.Gray)
        }
        Column {
            Text(chat.time, fontSize = 12.sp, color = Color.Gray)
            Image(
                painter = painterResource(Res.drawable.box),
                contentDescription = "Archivar chat",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { /* Aquí puedes agregar la lógica para archivar el chat */

                    }
                    .padding(4.dp)
            )
        }
    }
}
