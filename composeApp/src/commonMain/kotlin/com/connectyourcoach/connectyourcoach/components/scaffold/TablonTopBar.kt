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
fun TablonTopBar(onMoreOptions: () -> Unit) {
    TopAppBar(
        title = { Text("Tablón") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            IconButton(onClick = { onMoreOptions() }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
            }
        }
    )
}