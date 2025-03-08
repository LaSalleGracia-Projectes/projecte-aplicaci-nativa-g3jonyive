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
import com.connectyourcoach.connectyourcoach.views.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("registerPhoto") }
    val registerViewModel = remember { RegisterViewModel() }

    MaterialTheme {
        when (currentScreen) {
            "registerPhoto" -> RegisterPhotoUsernameView { currentScreen = "register" }
            "register" -> RegisterView(viewModel = registerViewModel) {
                currentScreen = "login"
            }
            "login" -> LoginView { currentScreen = "profile" }
            "profile" -> ProfileView(
                viewModel = registerViewModel, // Pass the same ViewModel
                onNavigateToSettings = { /* Add navigation logic */ },
                onLogout = { currentScreen = "login" }
            )
        }
        TablonView()
    }
}
