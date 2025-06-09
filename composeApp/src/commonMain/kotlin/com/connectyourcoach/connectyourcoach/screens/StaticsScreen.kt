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
import com.connectyourcoach.connectyourcoach.viewmodels.StaticsViewModel
import com.connectyourcoach.connectyourcoach.views.ControlPanelView
import com.connectyourcoach.connectyourcoach.views.StaticsView


class StaticsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel by remember { mutableStateOf(StaticsViewModel()) }

        StaticsView(
            viewModel = viewModel,
            onGoToControlPanel = {
                navigator.popAll()
                navigator.push(ControlPanelScreen())
            },
            onGoToProfile = {
                navigator.popAll()
                navigator.push(ProfileScreen())
            }
        )
    }
}
