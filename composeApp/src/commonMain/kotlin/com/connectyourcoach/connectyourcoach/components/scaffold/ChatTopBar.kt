package com.connectyourcoach.connectyourcoach.components.scaffold

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable

@Composable
fun ChatTopBar() {
    TopAppBar(
        title = { Text("Chat") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {

        }
    )
}