package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.ArchivedChatTopBar
import com.connectyourcoach.connectyourcoach.views.ArchivedChatsView

class ArchivedChatScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        BaseScaffold(
            navigator = navigator,
            topBar = { ArchivedChatTopBar(
                onBackClick = {
                    navigator?.pop()
                })
            },
        ) { paddingValues ->
            ArchivedChatsView(
                paddingValues = paddingValues,
                archivedChats = emptyList()
            ) { chatPreview ->

            }
        }
    }
}