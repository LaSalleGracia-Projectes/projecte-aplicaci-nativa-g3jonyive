package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.views.SettingsProfileView

class SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        SettingsProfileView(
            onBack = {
                // Quan es fa clic per tornar enrere, es torna a la pantalla anterior (Perfil)
                navigator?.pop()
            }
        )
    }
}

