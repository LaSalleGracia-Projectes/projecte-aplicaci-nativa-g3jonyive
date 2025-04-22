package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.screens.ArchivedChatScreen
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.box
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatListTopBar() {
    val navigator = LocalNavigator.current

    BaseTopBar(
        title = { Text("Chat") },
        actions = {
            Image(
                painter = painterResource(Res.drawable.box),
                contentDescription = "Ver chats archivados",
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        navigator?.push(ArchivedChatScreen())
                    }
                    .padding(8.dp)
            )
        }
    )
}
