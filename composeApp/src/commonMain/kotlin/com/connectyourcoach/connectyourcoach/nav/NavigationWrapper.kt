package com.connectyourcoach.connectyourcoach.nav

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.connectyourcoach.connectyourcoach.screens.LoginScreen
import com.connectyourcoach.connectyourcoach.screens.MainScreen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

@Composable
fun NavigationWrapper() {
    val user = Firebase.auth.currentUser

    val screen = if (user != null) MainScreen() else LoginScreen()

    Navigator(screen = screen) { navigator ->
        SlideTransition(navigator)
    }
}