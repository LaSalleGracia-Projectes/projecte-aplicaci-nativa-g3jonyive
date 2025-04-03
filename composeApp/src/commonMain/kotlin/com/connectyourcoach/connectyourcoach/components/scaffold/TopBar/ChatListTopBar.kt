package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable

@Composable
fun ChatListTopBar() {
    TopAppBar(
        title = { Text("Chat") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {

        }
    )
}