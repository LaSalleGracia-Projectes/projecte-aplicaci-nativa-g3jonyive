package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.viewmodels.ControlPanelViewModel
import com.connectyourcoach.connectyourcoach.views.ControlPanelView

class ControlPanelScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel by remember { mutableStateOf(ControlPanelViewModel()) }

        BaseScaffold(
            navigator = navigator,
            showBottomBar = false,
            topBar = {}
        ) { paddingValues ->
            ControlPanelView(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onLogout = {
                    navigator.popAll()
                    navigator.push(LoginScreen())
                },
                onGoToProfile = {
                    navigator.popAll()
                    navigator.push(ProfileScreen())
                }
            )
        }
    }
}
