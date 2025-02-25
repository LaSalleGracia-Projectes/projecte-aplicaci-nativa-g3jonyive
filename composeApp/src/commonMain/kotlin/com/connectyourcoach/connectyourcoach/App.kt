package com.connectyourcoach.connectyourcoach

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.connectyourcoach.connectyourcoach.views.LoginView
import com.connectyourcoach.connectyourcoach.views.ProfileView
import com.connectyourcoach.connectyourcoach.views.RegisterView
import com.connectyourcoach.connectyourcoach.views.SettingsProfileView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        LoginView()
    }
}