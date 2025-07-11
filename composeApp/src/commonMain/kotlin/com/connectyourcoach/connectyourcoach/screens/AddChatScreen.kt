package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.AddCardTopBar
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.AddChatTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.AddCardViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.AddChatViewModel
import com.connectyourcoach.connectyourcoach.views.AddCardView
import com.connectyourcoach.connectyourcoach.views.AddChatView

class AddChatScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(AddChatViewModel()) }

        BaseScaffold(
            navigator = navigator,
            topBar = {
                AddChatTopBar()
            },
        ) { paddingValues ->
            AddChatView(
                paddingValues = paddingValues,
                viewModel = viewModel,
            ) {
                navigator?.pop()
            }
        }
    }
}