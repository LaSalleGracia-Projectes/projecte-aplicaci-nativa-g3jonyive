package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar


import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.components.AvatarIcon

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
                AvatarIcon(
                    avatar = ""
                )
            }
        }
    )
}