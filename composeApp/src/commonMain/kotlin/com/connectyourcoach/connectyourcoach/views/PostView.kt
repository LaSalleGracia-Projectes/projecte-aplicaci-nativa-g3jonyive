package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                        text = "By ${post.user_id}",
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

