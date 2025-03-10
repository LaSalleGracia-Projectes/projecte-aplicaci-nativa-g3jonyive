package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(TablonViewModel()) }

        TablonView(
            viewModel = viewModel,
            onSignOut = {
                // A la sortida, pots gestionar la navegació, per exemple, tancar la sessió
                navigator?.popAll()
                navigator?.push(LoginScreen()) // Exemple de navegació a la pantalla de login
            },
            onInicio = {
                navigator?.push(MainScreen())
            },
            onChat = {
                // Afegeix la navegació del chat aquí si és necessari
            },
            onProfile = {
                navigator?.push(ProfileScreen())
            }
        )
    }
}
