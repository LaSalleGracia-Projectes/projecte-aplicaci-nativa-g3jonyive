package com.connectyourcoach.connectyourcoach

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.views.LoginView
import com.connectyourcoach.connectyourcoach.views.ProfileView
import com.connectyourcoach.connectyourcoach.views.RegisterPhotoUsernameView
import com.connectyourcoach.connectyourcoach.views.RegisterView
import com.connectyourcoach.connectyourcoach.views.SettingsProfileView
import org.auth.def.RegisterViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    val viewModel = remember { RegisterViewModel() }
    var currentScreen by remember { mutableStateOf("registerPhoto") }

    MaterialTheme {
        when (currentScreen) {
            "registerPhoto" -> RegisterPhotoUsernameView(viewModel) {
                currentScreen = "register"
            }
            "register" -> RegisterView(viewModel) {
                currentScreen = "login"
            }
            "login" -> LoginView()
        }
    }
}
