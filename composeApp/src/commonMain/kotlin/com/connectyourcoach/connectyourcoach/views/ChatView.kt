package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectyourcoach.connectyourcoach.viewmodels.ChatViewModel
import com.connectyourcoach.connectyourcoach.models.ChatModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatView(paddingValues: PaddingValues) {
    val viewModel = remember { ChatViewModel() }
    val messages = viewModel.messages
    val text = viewModel.currentMessage

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { viewModel.onMessageChange(it) },
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
            IconButton(onClick = { viewModel.sendMessage() }) {
                Icon(Icons.Filled.Send, contentDescription = "Enviar", tint = Color(0xFF173040))
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatModel) {
    val isCurrentUser = message.userId == "Tú"
    val bubbleColor = if (isCurrentUser) Color(0xFF5FD9AC) else Color(0xFFD9D8D2)
    val textColor = if (isCurrentUser) Color.White else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isCurrentUser) {
            AvatarIcon(message)
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
            AvatarIcon(message)
        }
    }
}

@Composable
fun AvatarIcon(message: ChatModel) {
    Icon(
        painter = painterResource(message.avatar),
        contentDescription = "Avatar",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray)
    )
}
