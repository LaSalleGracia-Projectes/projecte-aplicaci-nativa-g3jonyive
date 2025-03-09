package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.RegisterView

class RegisterScreen(function: () -> Unit) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        RegisterView(
            viewModel = RegisterViewModel(),
            onRegisterComplete = { navigator?.pop() } // Després de completar el registre, es torna enrere
        )
    }
}
