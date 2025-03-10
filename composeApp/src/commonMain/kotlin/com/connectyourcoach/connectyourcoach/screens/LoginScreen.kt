package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.LoginViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.SharedViewModel
import com.connectyourcoach.connectyourcoach.views.LoginView

class LoginScreen(private val sharedViewModel: SharedViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        LoginView(
            viewModel = LoginViewModel(),
            registerViewModel = sharedViewModel.registerViewModel.value,  // 💡 Passar el ViewModel registrat
            onLogin = { navigator?.push(MainScreen(sharedViewModel)) },
            onRegister = { navigator?.push(RegisterScreen(sharedViewModel)) }
        )
    }
}
