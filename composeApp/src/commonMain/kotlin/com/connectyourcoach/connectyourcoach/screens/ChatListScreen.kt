package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.ChatListTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.ListChatViewModel
import com.connectyourcoach.connectyourcoach.views.ListChatView

class ChatListScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        BaseScaffold(
            navigator = navigator,
            topBar = { ChatListTopBar() },
        ) { paddingValues ->
            ListChatView(
                viewModel = ListChatViewModel(),
                paddingValues = paddingValues
            ) { chatId ->
                navigator?.push(ChatScreen(
                    chatId = chatId
                ))
            }
        }
    }
}