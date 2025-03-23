package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.RegisterPhotoUsernameView

class RegisterScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val registerViewModel = remember { RegisterViewModel() }

        RegisterPhotoUsernameView(
            viewModel = registerViewModel,
            onRegisterComplete = {
                navigator?.pop()
            }
        )
    }
}