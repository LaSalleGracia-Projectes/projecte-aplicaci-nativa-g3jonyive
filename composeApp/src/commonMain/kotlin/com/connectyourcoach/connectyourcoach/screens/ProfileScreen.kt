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

        ProfileView(
            viewModel = RegisterViewModel(),
            onNavigateToSettings = {
                navigator?.push(SettingsScreen { var currentScreen = "profile" })
            },
            onLogout = {
                println("Usuari tancat de la sessió.")
            }
        )
    }
}

