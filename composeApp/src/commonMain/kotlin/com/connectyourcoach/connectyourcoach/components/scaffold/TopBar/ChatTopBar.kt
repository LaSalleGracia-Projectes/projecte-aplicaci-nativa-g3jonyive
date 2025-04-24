package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar


import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ChatTopBar(
    userName: String,
    onBackClick: () -> Unit,
    onAvatarClick: () -> Unit
) {
    BaseTopBar(
        title = { Text(userName) },
        onBack = onBackClick,
        actions = {
            IconButton(onClick = onAvatarClick) {

            }
        }
    )
}