package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.TablonBottomBar
import com.connectyourcoach.connectyourcoach.views.TablonView

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val tablonViewModel = TablonViewModel()

        TablonView(
            viewModel = tablonViewModel,
            onSignOut = {
                // A la sortida, pots gestionar la navegació, per exemple, tancar la sessió
                navigator?.popAll()
                navigator?.push(LoginScreen()) // Exemple de navegació a la pantalla de login
            }
        )

        TablonBottomBar(
            viewModel = tablonViewModel,
            onInicio = {
                navigator?.push(MainScreen())
            },
            onChat = {

            },
            onProfile = {
                navigator?.push(ProfileScreen())
            }
        )
    }
}
