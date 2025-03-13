package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable

@Composable
fun SettingsTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text("Settings") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}