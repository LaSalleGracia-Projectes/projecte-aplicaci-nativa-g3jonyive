package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.ArchivedChatTopBar
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.views.ArchivedChatView

class ArchivedChatScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val chatsArchivados = remember { mutableStateListOf<FirestoreChat>() }

        BaseScaffold(
            navigator = navigator,
            topBar = {
                ArchivedChatTopBar(
                    onBackClick = { navigator?.pop() }
                )
            }
        ) { paddingValues ->
            ArchivedChatView(
                paddingValues = paddingValues,
                chatsArchivados = chatsArchivados
            ) { chatPreview ->
                chatsArchivados.remove(chatPreview)
            }
        }
    }
}
