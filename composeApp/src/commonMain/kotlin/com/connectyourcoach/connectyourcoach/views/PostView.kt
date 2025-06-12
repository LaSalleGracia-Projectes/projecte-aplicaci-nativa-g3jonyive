package com.connectyourcoach.connectyourcoach.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.viewmodels.PostViewModel

// Definir la paleta de colores personalizada
private val DarkBlue = Color(0xFF173040)
private val Turquoise = Color(0xFF038C8C)
private val MintGreen = Color(0xFF5FD9AC)
private val LightGray = Color(0xFFD9D8D2)
private val Beige = Color(0xFFBFBCB4)

// Definir el tema con la paleta de colores
private val customColors = darkColors(
    primary = Turquoise,
    secondary = MintGreen,
    background = DarkBlue,
    surface = LightGray,
    onPrimary = Color.White,
    onSecondary = DarkBlue,
    onBackground = LightGray,
    onSurface = DarkBlue
)

@Composable
fun PostView(viewModel: PostViewModel, paddingValues: PaddingValues) {
    val post by viewModel.post
    val user by viewModel.user
    val isLiked by viewModel.isLiked
    val scrollState = rememberScrollState()

    MaterialTheme(colors = customColors) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
                .verticalScroll(scrollState), // <- Aquí añadimos scroll
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            post?.let { post ->
                AsyncImage(
                    model = post.photo,
                    contentDescription = post.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "By ${user?.username ?: "Unknown User"}",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "On ${post.created_at}",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                if (post.updated_at != post.created_at) {
                    Text(
                        text = "Updated on ${post.updated_at}",
                        style = MaterialTheme.typography.body2,
                        color = Beige,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Text(
                    text = "Price: ${post.price}",
                    style = MaterialTheme.typography.body2,
                    color = MintGreen,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    LikeButton(
                        isLiked = isLiked,
                        onToggleLike = {
                            viewModel.onLike()
                        }
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        text = "${viewModel.likes.value} Likes",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(16.dp)
                )
            } ?: run {
                Text(
                    text = "Post not found",
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}

@Composable
fun LikeButton(
    isLiked: Boolean,
    onToggleLike: () -> Unit
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isLiked) {
        scale.snapTo(1f)
        scale.animateTo(
            targetValue = 1.3f,
            animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 100, easing = FastOutLinearInEasing)
        )
    }

    IconButton(onClick = onToggleLike) {
        Icon(
            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isLiked) "Unlike" else "Like",
            tint = if (isLiked) Color.Red else Color.Gray,
            modifier = Modifier.scale(scale.value)
        )
    }
}

