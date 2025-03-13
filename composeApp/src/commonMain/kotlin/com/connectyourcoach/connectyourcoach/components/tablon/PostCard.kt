package com.connectyourcoach.connectyourcoach.components.tablon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.models.Post

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box {
            AsyncImage(
                model = post.photo ?: "",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colors.surface.copy(alpha = 0.7f))
            ) {
                Text(text = post.title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = post.description, style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "User ID: ${post.user_id}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Price: \$${post.price}", style = MaterialTheme.typography.body2)
            }
        }
    }
}