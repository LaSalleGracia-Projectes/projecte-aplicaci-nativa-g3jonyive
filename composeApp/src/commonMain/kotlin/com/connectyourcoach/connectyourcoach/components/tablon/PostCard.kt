package com.connectyourcoach.connectyourcoach.components.tablon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.models.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PostCard(post: Post, backgroundColor: Color, onClick: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .background(backgroundColor)
            .padding(8.dp)
            .clickable { onClick(post.id.toString()) },
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                .padding(16.dp)
        ) {
            Text(
                text = truncatedContent(post.title),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = truncatedContent(post.description),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User ID: ${post.user_id}",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Price: ${post.price}€",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

fun truncatedContent(content: String): String {
    return if (content.length > 30) {
        content.substring(0, 30) + "..."
    } else {
        content
    }
}