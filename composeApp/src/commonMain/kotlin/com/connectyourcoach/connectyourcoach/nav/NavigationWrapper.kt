package com.connectyourcoach.connectyourcoach.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.connectyourcoach.connectyourcoach.screens.LoginScreen

@Composable
fun NavigationWrapper() {
    Navigator(screen = LoginScreen()) { navigator ->
        SlideTransition(navigator)
    }
}