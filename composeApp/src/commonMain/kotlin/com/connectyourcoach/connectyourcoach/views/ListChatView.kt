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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.connectyourcoach.connectyourcoach.models.ChatPreview
import com.connectyourcoach.connectyourcoach.viewmodels.ListChatViewModel
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.box
import org.jetbrains.compose.resources.painterResource

@Composable
fun ListChatView(
    paddingValues: PaddingValues,
    onChatSelected: (ChatPreview) -> Unit,
    viewModel: ListChatViewModel = ListChatViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        OutlinedTextField(
            value = viewModel.searchText,
            onValueChange = { viewModel.searchText = it },
            label = { Text("Buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn {
            items(viewModel.filteredChats) { chat ->
                ChatListItem(
                    chat = chat,
                    onClick = { onChatSelected(chat) },
                    onArchive = { viewModel.archiveChat(chat) }
                )
            }
        }
    }
}

@Composable
fun ChatListItem(chat: ChatPreview, onClick: () -> Unit, onArchive: () -> Unit) {
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
        Column(horizontalAlignment = Alignment.End) {
            Text(chat.time, fontSize = 12.sp, color = Color.Gray)
            Image(
                painter = painterResource(Res.drawable.box),
                contentDescription = "Archivar chat",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { onArchive() }
                    .padding(4.dp)
            )
        }
    }
}
