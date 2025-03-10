package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(TablonViewModel()) }

        TablonView(
            viewModel = viewModel,
            onInicio = {
                navigator?.push(MainScreen())
            },
            onChat = {
                // Afegeix la navegació del chat aquí si és necessari
            },
            onProfile = {
                navigator?.push(ProfileScreen())
            }
        )
    }
}
