package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar


import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.components.AvatarIcon
import com.connectyourcoach.connectyourcoach.models.FirestoreUser

@Composable
fun ChatTopBar(
    user: FirestoreUser,
    onBackClick: () -> Unit,
    onAvatarClick: () -> Unit
) {
    BaseTopBar(
        title = { Text(user.username) },
        onBack = onBackClick,
        actions = {
            IconButton(onClick = onAvatarClick) {
                AvatarIcon(
                    avatar = user.photoUrl,
                )
            }
        }
    )
}