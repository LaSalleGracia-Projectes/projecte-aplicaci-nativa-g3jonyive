package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.AddCardTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.AddCardViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.AddCardView

class AddCardScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(AddCardViewModel()) }

        BaseScaffold(
            navigator = navigator,
            topBar = {
                AddCardTopBar {
                    navigator?.pop()
                }
            },
        ) { paddingValues ->
            AddCardView(
                paddingValues = paddingValues,
                viewModel = viewModel,
            ) {
                navigator?.pop()
            }
        }
    }
}
