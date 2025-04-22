package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar


import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.views.AvatarIcon
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo

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
                AvatarIcon(Res.drawable.logo)
            }
        }
    )
}