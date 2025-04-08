package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

@Composable
fun ArchivedChatsView(paddingValues: PaddingValues, archivedChats: List<ChatPreview>, onChatSelected: (ChatPreview) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
        // Si no hay chats archivados
        if (archivedChats.isEmpty()) {
            Text("No tienes chats archivados.", color = Color.Gray)
        } else {
            LazyColumn {
                items(archivedChats) { chat ->
                    ArchivedChatItem(chat = chat, onClick = { onChatSelected(chat) })
                }
            }
        }
    }
}

@Composable
fun ArchivedChatItem(chat: ChatPreview, onClick: () -> Unit) {
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
        Text(chat.time, fontSize = 12.sp, color = Color.Gray)
    }
}
