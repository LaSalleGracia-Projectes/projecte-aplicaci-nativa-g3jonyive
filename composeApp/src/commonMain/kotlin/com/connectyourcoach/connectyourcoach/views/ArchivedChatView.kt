package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import connectyourcoach.composeapp.generated.resources.undo
import org.jetbrains.compose.resources.painterResource

@Composable
fun ArchivedChatView(
    chatsArchivados: List<FirestoreChat>,
    paddingValues: PaddingValues,
    onRestore: (FirestoreChat) -> Unit
) {
    if (chatsArchivados.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay chats archivados", color = Color.Gray)
        }
    } else {
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(chatsArchivados) { chat ->
                ChatArchivedItem(chat = chat, onRestore = { onRestore(chat) })
            }
        }
    }
}

@Composable
fun ChatArchivedItem(chat: FirestoreChat, onRestore: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Usuario", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Ultimo mensaje", fontSize = 14.sp, color = Color.Gray)
        }
        Image(
            painter = painterResource(Res.drawable.undo),
            contentDescription = "Restaurar chat",
            modifier = Modifier
                .size(36.dp)
                .clickable { onRestore() }
                .padding(4.dp)
        )
    }
}
