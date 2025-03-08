package com.connectyourcoach.connectyourcoach

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.connectyourcoach.connectyourcoach.views.LoginView
import com.connectyourcoach.connectyourcoach.views.ProfileView
import com.connectyourcoach.connectyourcoach.views.RegisterPhotoUsernameView
import com.connectyourcoach.connectyourcoach.views.RegisterView


@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("registerPhoto") }

    MaterialTheme {
        when (currentScreen) {
            "registerPhoto" -> RegisterPhotoUsernameView { currentScreen = "register" }
            "register" -> RegisterView { currentScreen = "login" }
            "login" -> LoginView { currentScreen = "profile" }
            "profile" -> ProfileView(
                onNavigateToSettings = { /* Afegir navegació a Settings si cal */ },
                onLogout = { currentScreen = "login" }
            )
        }
    }
}
