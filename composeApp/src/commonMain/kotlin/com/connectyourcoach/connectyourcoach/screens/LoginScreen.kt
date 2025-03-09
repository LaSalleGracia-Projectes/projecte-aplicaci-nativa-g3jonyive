package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.LoginViewModel
import com.connectyourcoach.connectyourcoach.views.LoginView

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        LoginView(
            viewModel = LoginViewModel(),
            onLogin = { navigator?.push(MainScreen()) },
            onRegister = { navigator?.push(RegisterScreen()) }
        )
    }
}