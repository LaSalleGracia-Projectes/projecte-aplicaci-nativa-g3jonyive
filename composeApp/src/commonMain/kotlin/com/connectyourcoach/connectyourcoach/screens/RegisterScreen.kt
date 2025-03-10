package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.SharedViewModel
import com.connectyourcoach.connectyourcoach.views.RegisterPhotoUsernameView
import com.connectyourcoach.connectyourcoach.views.RegisterView

class RegisterScreen(private val sharedViewModel: SharedViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var showPhotoUsernameView by remember { mutableStateOf(true) }

        if (showPhotoUsernameView) {
            RegisterPhotoUsernameView(
                viewModel = sharedViewModel.registerViewModel.value,
                onRegisterComplete = { showPhotoUsernameView = false }
            )
        } else {
            RegisterView(
                viewModel = sharedViewModel.registerViewModel.value,
                onRegisterComplete = { navigator?.pop() }
            )
        }
    }
}
