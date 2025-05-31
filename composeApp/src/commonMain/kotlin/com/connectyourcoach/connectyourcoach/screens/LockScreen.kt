package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.connectyourcoach.connectyourcoach.viewmodels.LockViewModel
import com.connectyourcoach.connectyourcoach.views.LockView

class LockScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = LockViewModel()

        LockView(
            viewModel = viewModel,
            onBack = {
                navigator.pop()
            }
        )
    }
}
