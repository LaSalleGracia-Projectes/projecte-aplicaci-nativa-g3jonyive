package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.viewmodels.SharedViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView

class MainScreen( val sharedViewModel: SharedViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val tablonViewModel = TablonViewModel()
        val sharedViewModel = remember { sharedViewModel } // Es fa servir l'instància passada

        TablonView(
            viewModel = tablonViewModel,
            onSignOut = {
                // A la sortida, pots gestionar la navegació, per exemple, tancar la sessió
                navigator?.popAll()
                navigator?.push(LoginScreen(sharedViewModel = sharedViewModel)) // Passar el sharedViewModel ja existent
            },
            onInicio = {
                navigator?.push(MainScreen(sharedViewModel)) // Passar el sharedViewModel ja existent
            },
            onChat = {
                // Afegeix la navegació del chat aquí si és necessari
            },
            onProfile = {
                navigator?.push(ProfileScreen(sharedViewModel = sharedViewModel)) // Passar el sharedViewModel ja existent
            }
        )
    }
}
