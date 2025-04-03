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
import com.connectyourcoach.connectyourcoach.views.AvatarIcon
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo

@Composable
fun ChatTopBar(
    userName: String,
    onBackClick: () -> Unit,
    onAvatarClick: () -> Unit
) {
    TopAppBar(
        title = { Text(userName) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
            }
        },
        actions = {
            IconButton(onClick = onAvatarClick) {
                AvatarIcon(Res.drawable.logo)
            }
        }
    )
}