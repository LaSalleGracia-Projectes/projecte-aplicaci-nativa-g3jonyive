package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import com.connectyourcoach.connectyourcoach.viewmodels.ListChatViewModel
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.box
import connectyourcoach.composeapp.generated.resources.logo
import dev.gitlive.firebase.firestore.toDuration
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toDateTimePeriod
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ListChatView(
    viewModel: ListChatViewModel,
    paddingValues: PaddingValues,
    onChatSelected: (FirestoreChat) -> Unit
) {
    val chats by viewModel.chats.collectAsState(initial = emptyList())
    val querySearch by viewModel.querySearch

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        OutlinedTextField(
            value = querySearch,
            onValueChange = { viewModel.updateQuerySearch(it) },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn {
            items(chats) { chat ->
                viewModel.getUser(chat).collectAsState(initial = null).value?.let {
                    ChatListItem(
                        chat = chat,
                        user = it,
                        onClick = { onChatSelected(chat) },
                        onArchive = {

                        }
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
    onClick: () -> Unit,
    onArchive: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = user.photoUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentDescription = "Avatar",
            loading = {
                CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier.size(50.dp)
                )
            },
            error = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    modifier = Modifier.size(50.dp),
                    contentDescription = "Error on loading image",
                )
            }
        )

        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(user.username, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(chat.messages.last().message, fontSize = 14.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {
            val date = chat.created.toDuration().let { period ->
                    val createdDateTime = kotlinx.datetime.Clock.System.now()
                        .minus(period)
                    "${createdDateTime.toLocalDateTime(TimeZone.UTC).date}"
                }
            Text(date, fontSize = 12.sp, color = Color.Gray)
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


