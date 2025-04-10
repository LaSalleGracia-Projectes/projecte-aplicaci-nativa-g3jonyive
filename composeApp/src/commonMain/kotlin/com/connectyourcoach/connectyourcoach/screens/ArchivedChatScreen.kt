package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.ArchivedChatTopBar
import com.connectyourcoach.connectyourcoach.views.ArchivedChatView
import com.connectyourcoach.connectyourcoach.views.ChatPreview
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo

class ArchivedChatScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val chatsArchivados = remember {
            mutableStateListOf(
                ChatPreview("1", "María López", "Nos vemos mañana!", "10:45 AM", Res.drawable.logo),
                ChatPreview("2", "Juan Pérez", "¿Qué tal todo?", "09:30 AM", Res.drawable.logo),
                ChatPreview("3", "Laura García", "Gracias! 😊", "20:50 PM", Res.drawable.logo),
            )
        }
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
