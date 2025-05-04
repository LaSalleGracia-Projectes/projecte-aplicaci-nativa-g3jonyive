package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectyourcoach.connectyourcoach.components.AvatarIcon
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import com.connectyourcoach.connectyourcoach.viewmodels.ListChatViewModel
import dev.gitlive.firebase.firestore.toDuration
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ListChatView(
    viewModel: ListChatViewModel,
    paddingValues: PaddingValues,
    onChatSelected: (String, FirestoreUser) -> Unit
) {
    val chats by viewModel.chats.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (chats.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp)
                )
            }
            return@Column
        }

        LazyColumn {
            items(chats) { chat ->
                viewModel.getUser(chat).collectAsState(initial = null).value?.let { user ->
                    ChatListItem(
                        chat = chat,
                        user = user,
                        onClick = { onChatSelected(
                            chat.id,
                            user
                        ) }
                    )
                }
            }
        }
    }
}

@Composable
fun ChatListItem(
    chat: FirestoreChat,
    user: FirestoreUser,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarIcon(user.photoUrl)

        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(user.username, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(chat.messages.last().message, fontSize = 14.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {
            val date = chat.messages.last().created.toDuration().let { period ->
                    val createdDateTime = kotlinx.datetime.Clock.System.now()
                        .minus(period)
                    "${createdDateTime.toLocalDateTime(TimeZone.UTC).date}"
                }
            Text(date, fontSize = 12.sp, color = Color.Gray)
        }
    }
}


