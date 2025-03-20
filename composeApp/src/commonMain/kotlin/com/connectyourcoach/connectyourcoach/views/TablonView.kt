package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.components.tablon.PostCard
import com.connectyourcoach.connectyourcoach.components.tablon.TablonSearchBar
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel

@Composable
fun TablonView(viewModel: TablonViewModel, paddingValues: PaddingValues, onClickPost: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val posts by viewModel.posts
        val query by viewModel.query
        val loading by viewModel.loading

        TablonSearchBar(
            query = query,
            onSearch = { viewModel.onSearch(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (loading) {
            CircularProgressIndicator()
            return@Column
        }

        if (posts.isNotEmpty()) {
            LazyColumn {
                items(posts) { post ->
                    PostCard(
                        post = post,
                        onClick = onClickPost
                    )
                }
            }
        } else {
            Text(
                text = "No posts found",
                style = MaterialTheme.typography.h6
            )
        }
    }
}
