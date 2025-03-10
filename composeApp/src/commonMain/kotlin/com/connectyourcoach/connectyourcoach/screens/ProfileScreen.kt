package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.SharedViewModel
import com.connectyourcoach.connectyourcoach.views.ProfileView

class ProfileScreen(private val sharedViewModel: SharedViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        ProfileView(
            viewModel = sharedViewModel.registerViewModel.value,  // 💡 Mostrar dades registrades
            onNavigateToSettings = { navigator?.push(SettingsScreen()) },
            onLogout = {
                println("Usuari tancat de la sessió.")
                navigator?.popAll()
                navigator?.push(LoginScreen(sharedViewModel))
            },
            onInicio = { navigator?.push(MainScreen(sharedViewModel)) },
            onChat = {},
            onProfile = { navigator?.push(ProfileScreen(sharedViewModel)) }
        )
    }
}
