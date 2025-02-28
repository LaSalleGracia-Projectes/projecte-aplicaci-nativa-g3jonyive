package com.connectyourcoach.connectyourcoach

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import com.connectyourcoach.connectyourcoach.views.LoginView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        RegisterView { }
    }
}