package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.RegisterView

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val registerViewModel = remember { RegisterViewModel() }

        RegisterView(
            viewModel = registerViewModel,
            onRegisterComplete = {
                navigator?.replaceAll(TablonScreen())
            },
            onLogin = { navigator?.pop() }
        )
    }
}
