package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.ChatTopBar
import com.connectyourcoach.connectyourcoach.views.ChatView

class ChatScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        BaseScaffold(
            navigator = navigator,
            topBar = { ChatTopBar() },
        ) { paddingValues ->
            ChatView(
                paddingValues = paddingValues
            )
        }
    }
}