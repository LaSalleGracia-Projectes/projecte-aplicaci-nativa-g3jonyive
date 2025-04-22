package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Black = Color(0xFF0A0A0A)

@Composable
fun ProfileTopBar(onNavigateToSettings: () -> Unit) {
    BaseTopBar(
        title = { Text("User profile") },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings,
                    contentDescription = "Configuration",
                    tint = Black
                )
            }
        }
    )
}