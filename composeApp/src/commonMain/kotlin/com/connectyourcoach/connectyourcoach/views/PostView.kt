package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.viewmodels.PostViewModel

@Composable
fun PostView(viewModel: PostViewModel, paddingValues: PaddingValues) {
    val post by viewModel.post

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        post?.let { post ->
            AsyncImage(
                model = post.photo,
                contentDescription = post.title,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(16.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "By ${post.user_id}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "On ${post.created_at}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(16.dp)
                )
                if (post.updated_at != post.created_at) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Updated on ${post.updated_at}",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Text(
                    text = "Price: ${post.price}",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp)
            )
        } ?: run {
            Text("Post not found")
        }
    }
}