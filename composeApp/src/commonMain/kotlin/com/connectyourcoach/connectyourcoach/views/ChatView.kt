package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.connectyourcoach.connectyourcoach.components.AvatarIcon
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.models.FirestoreChatMessage
import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import com.connectyourcoach.connectyourcoach.viewmodels.ChatViewModel
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatView(
    viewModel: ChatViewModel,
    paddingValues: PaddingValues
) {
    val chat by viewModel.chat.collectAsState(initial = null)

    val user by viewModel.user.collectAsState(initial = null)

    val newMessage by viewModel.message

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
            items(chat?.messages?.reversed() ?: emptyList()) { message ->
                ChatBubble(
                    message = message,
                    user = viewModel.firestoreUserRepository.getUserById(message.sender).collectAsState(initial = user).value
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newMessage,
                onValueChange = { viewModel.updateMessage(it) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Message...") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color(0xFF173040),
                    unfocusedBorderColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        viewModel.sendMessage()
                    }
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { viewModel.sendMessage() }
            ) {
                Icon(Icons.Filled.Send, contentDescription = "Send", tint = Color(0xFF173040))
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: FirestoreChatMessage,
    user: FirestoreUser?
) {
    val isCurrentUser = message.sender == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser) Color(0xFF5FD9AC) else Color(0xFFD9D8D2)
    val textColor = if (isCurrentUser) Color.White else Color.Black
    val arragement = if (isCurrentUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = arragement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isCurrentUser) {
            AvatarIcon(user?.photoUrl ?: "")
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(bubbleColor)
                .padding(12.dp)
        ) {
            Text(text = message.message, fontSize = 16.sp, color = textColor)
        }

        if (isCurrentUser) {
            Spacer(modifier = Modifier.width(16.dp))
            AvatarIcon(user?.photoUrl ?: "")
        }
    }
}