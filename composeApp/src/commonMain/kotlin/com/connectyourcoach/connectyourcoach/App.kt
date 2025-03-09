package com.connectyourcoach.connectyourcoach

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.connectyourcoach.connectyourcoach.views.*

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }  // Aquí es canvia a "login"
    val registerViewModel = remember { RegisterViewModel() }

    MaterialTheme {
        when (currentScreen) {
            "registerPhoto" -> RegisterPhotoUsernameView { currentScreen = "register" }
            "register" -> RegisterView(viewModel = registerViewModel) { currentScreen = "login" }
            "login" -> LoginView { currentScreen = "tablon" }
            "profile" -> ProfileView(
                viewModel = registerViewModel,
                onNavigateToSettings = { currentScreen = "settings" },
                onLogout = { currentScreen = "login" },
                navigateToInicio = { currentScreen = "tablon" }
            )
            "tablon" -> TablonView(
                navigateToProfile = { currentScreen = "profile" },
                navigateToInicio = { currentScreen = "tablon" }
            )
            "settings" -> SettingsProfileView { currentScreen = "profile" }
        }
    }
}
