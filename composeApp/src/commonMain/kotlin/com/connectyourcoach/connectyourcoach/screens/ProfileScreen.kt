package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.ProfileTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.ProfileViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.ProfileView
import com.connectyourcoach.connectyourcoach.views.TablonView

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(ProfileViewModel()) }

        BaseScaffold(
            navigator = navigator,
            topBar = { ProfileTopBar(onNavigateToSettings = { navigator?.push(SettingsScreen()) }) },
        ) { paddingValues ->
            ProfileView(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onLogout = {
                    navigator?.popAll()
                    navigator?.push(LoginScreen())
                }
            )
        }
    }
}
