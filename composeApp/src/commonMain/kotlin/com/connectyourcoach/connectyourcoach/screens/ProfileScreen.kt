package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.ProfileView
import com.connectyourcoach.connectyourcoach.views.TablonView

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val registerViewModel = RegisterViewModel()

        // Passant les funcions de navegació a ProfileView
        ProfileView(
            viewModel = registerViewModel,
            onNavigateToSettings = {
                navigator?.push(SettingsScreen { var currentScreen = "profile" })
            },
            onLogout = {
                println("Usuari tancat de la sessió.")
                navigator?.popAll() // Tanca totes les pantalles
                navigator?.push(LoginScreen()) // Navega a la pantalla de login
            },
            // Funcions de navegació per a BottomBar
            onInicio = {
                navigator?.push(MainScreen()) // Navega a la pantalla principal
            },
            onChat = {

            },
            onProfile = {
                navigator?.push(ProfileScreen())
            }
        )
    }
}
