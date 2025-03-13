package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.RegisterPhotoUsernameView
import com.connectyourcoach.connectyourcoach.views.RegisterView

class RegisterScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val registerViewModel by remember { mutableStateOf(RegisterViewModel()) }
        var showPhotoUsernameView by remember { mutableStateOf(false) }

        if (showPhotoUsernameView) {
            RegisterPhotoUsernameView(
                viewModel = registerViewModel,
                onRegisterComplete = { showPhotoUsernameView = false },
                onLogin = { navigator?.pop() }
            )
        } else {
            RegisterView(
                viewModel = registerViewModel,
                onRegisterComplete = { navigator?.pop() },
                onLogin = { navigator?.pop() }
            )
        }
    }
}
