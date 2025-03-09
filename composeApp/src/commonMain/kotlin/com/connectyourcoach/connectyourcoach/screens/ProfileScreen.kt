package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.ProfileView

class ProfileScreen(function: () -> Unit) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        ProfileView(
            viewModel = RegisterViewModel(),
            onNavigateToSettings = {
                // Canvia a la pantalla de configuració
                navigator?.push(SettingsScreen { var currentScreen = "profile" })
            },
            onLogout = {
                // Aquí pots implementar el que fa el logout, com tancar sessió de Firebase
                println("Usuari tancat de la sessió.")
            }
        )
    }
}

