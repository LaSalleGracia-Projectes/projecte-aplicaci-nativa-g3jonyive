package com.connectyourcoach.connectyourcoach.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.connectyourcoach.connectyourcoach.screens.LoginScreen
import com.connectyourcoach.connectyourcoach.screens.MainScreen
import com.connectyourcoach.connectyourcoach.viewmodels.SharedViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

@Composable
fun NavigationWrapper() {
    val user = Firebase.auth.currentUser
    val sharedViewModel = remember { SharedViewModel() } // ✅ Inicialitzar SharedViewModel

    val screen = if (user != null) MainScreen(sharedViewModel) else LoginScreen(sharedViewModel) // ✅ Passar el ViewModel

    Navigator(screen = screen) { navigator ->
        SlideTransition(navigator)
    }
}
