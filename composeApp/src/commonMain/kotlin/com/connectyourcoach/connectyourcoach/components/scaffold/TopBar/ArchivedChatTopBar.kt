package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable


@Composable
fun ArchivedChatTopBar(
    onBackClick: () -> Unit,
){
    TopAppBar(
        title = { Text("Chats Archivados") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
        },
        actions = {
            IconButton(onClick = onBackClick) {

            }
        }
    )
}