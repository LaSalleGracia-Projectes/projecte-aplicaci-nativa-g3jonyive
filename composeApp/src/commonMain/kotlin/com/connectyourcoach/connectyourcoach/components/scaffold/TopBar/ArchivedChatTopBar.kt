package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun ArchivedChatTopBar(
    onBackClick: () -> Unit,
){
    BaseTopBar(
        title = { Text("Chats Archivados") },
        onBack = onBackClick
    )
}