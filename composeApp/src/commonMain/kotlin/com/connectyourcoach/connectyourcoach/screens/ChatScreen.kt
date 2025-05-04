package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.ChatListTopBar
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.ChatTopBar
import com.connectyourcoach.connectyourcoach.models.FirestoreChat
import com.connectyourcoach.connectyourcoach.models.FirestoreUser
import com.connectyourcoach.connectyourcoach.viewmodels.ChatViewModel
import com.connectyourcoach.connectyourcoach.views.ChatView

class ChatScreen(
    private val chatId: String,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val viewModel = ChatViewModel(chatId = chatId)

        BaseScaffold(
            navigator = navigator,
            topBar = { ChatTopBar(
                userName = "Usuario",
                onAvatarClick = {  },
                onBackClick = {
                    navigator?.pop()
                })
            },
            showBottomBar = false
        ) { paddingValues ->
            ChatView(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }
    }
}