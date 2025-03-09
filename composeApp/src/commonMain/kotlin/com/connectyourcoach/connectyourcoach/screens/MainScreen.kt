package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        TablonView(
            viewModel = TablonViewModel(),
            onSignOut = {
                navigator?.popAll()
                navigator?.push(LoginScreen())
            }
        )
    }
}