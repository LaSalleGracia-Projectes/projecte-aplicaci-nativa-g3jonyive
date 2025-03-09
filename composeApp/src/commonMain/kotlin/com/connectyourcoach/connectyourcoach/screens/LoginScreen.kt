package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.connectyourcoach.connectyourcoach.views.LoginView

class LoginScreen(private val function: () -> Unit) : Screen {

    @Composable
    override fun Content() {
        LoginView(function = function)
    }
}