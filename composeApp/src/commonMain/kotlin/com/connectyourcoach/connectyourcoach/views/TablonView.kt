package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.components.tablon.PostCard
import com.connectyourcoach.connectyourcoach.components.tablon.TablonSearchBar
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel

private val DarkBlue = Color(0xFF173040)
private val Turquoise = Color(0xFF038C8C)
private val MintGreen = Color(0xFF5FD9AC)
private val LightGray = Color(0xFFD9D8D2)
private val Beige = Color(0xFFBFBCB4)

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
fun TablonView(viewModel: TablonViewModel, paddingValues: PaddingValues, onClickPost: (String) -> Unit) {
    val posts by viewModel.posts
    val query by viewModel.query
    val loading by viewModel.loading

    MaterialTheme(colors = customColors) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(8.dp)
            ) {
                TablonSearchBar(
                    query = query,
                    onSearch = { viewModel.onSearch(it) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (loading) {
                CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                return@Column
            }

            if (posts.isNotEmpty()) {
                LazyColumn {
                    items(posts) { post ->
                        PostCard(
                            post = post,
                            onClick = onClickPost,
                            backgroundColor = MaterialTheme.colors.surface
                        )
                    }
                }
            } else {
                Text(
                    text = "No posts found",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
