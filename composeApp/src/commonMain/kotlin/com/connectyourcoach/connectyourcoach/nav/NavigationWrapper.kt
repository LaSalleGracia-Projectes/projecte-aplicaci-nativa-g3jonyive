package com.connectyourcoach.connectyourcoach.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.connectyourcoach.connectyourcoach.screens.LoginScreen
import com.connectyourcoach.connectyourcoach.screens.MainScreen
import com.connectyourcoach.connectyourcoach.screens.ProfileScreen
import com.connectyourcoach.connectyourcoach.screens.SettingsScreen
import com.connectyourcoach.connectyourcoach.screens.RegisterScreen // Afegeix la pantalla de registre
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

@Composable
fun NavigationWrapper() {
    // Estat mutable per controlar la pantalla actual
    var currentScreen by remember { mutableStateOf("login") }

    // Comprovem l'usuari autenticat de Firebase
    val user = Firebase.auth.currentUser

    // Estableix la pantalla inicial segons si l'usuari està autenticat
    val initialScreen = if (user != null) "main" else "login"

    // Es defineix la vista de navegació
    Navigator(screen = when (currentScreen) {
        "login" -> LoginScreen {
            // Quan l'usuari vol registrar-se
            currentScreen = "register"
        }
        "register" -> RegisterScreen {
            // Quan l'usuari es registra i s'acaba
            currentScreen = "login"
        }
        "profile" -> ProfileScreen { currentScreen = "settings" }
        "settings" -> SettingsScreen { currentScreen = "profile" }
        "main" -> MainScreen { currentScreen = "profile" }
        else -> LoginScreen { currentScreen = "main" } // Fallback per a qualsevol altre estat
    }) { navigator ->
        SlideTransition(navigator)
    }
}
