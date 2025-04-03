package com.connectyourcoach.connectyourcoach.components.tablon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.models.Post

@Composable
fun PostCard(post: Post, backgroundColor: Color, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .background(backgroundColor)
            .padding(8.dp)
            .clickable { onClick(post.id) },
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.onSurface // Fondo de la tarjeta en gris claro
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f)) // Superposición en azul oscuro
                .padding(16.dp)
        ) {
            Text(
                text = truncatedContent(post.title),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary // Turquesa para títulos
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = truncatedContent(post.description),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onPrimary // Beige para descripciones
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User ID: ${post.user_id}",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: \$${post.price}",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondary // Verde menta para destacar el precio
            )
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
