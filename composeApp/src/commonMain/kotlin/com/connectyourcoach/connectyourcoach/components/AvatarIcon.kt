package com.connectyourcoach.connectyourcoach.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

@Composable
fun AvatarIcon(avatar: String) {
    SubcomposeAsyncImage(
        model = avatar,
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
}
