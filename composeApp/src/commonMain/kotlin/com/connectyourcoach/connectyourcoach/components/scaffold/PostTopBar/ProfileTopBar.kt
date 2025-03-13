package com.connectyourcoach.connectyourcoach.components.scaffold.PostTopBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable

@Composable
fun ProfileTopBar(onNavigateToSettings: () -> Unit) {
    TopAppBar(
        title = { Text("User profile") },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Configuration")
            }
        }
    )
}