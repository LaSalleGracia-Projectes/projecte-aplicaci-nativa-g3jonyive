package com.connectyourcoach.connectyourcoach.components.scaffold

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.connectyourcoach.connectyourcoach.screens.ChatScreen
import com.connectyourcoach.connectyourcoach.screens.TablonScreen
import com.connectyourcoach.connectyourcoach.screens.ProfileScreen

@Composable
fun BaseBottomBar(
    navigator: Navigator?
) {
    var selectedItem by remember { mutableStateOf(0) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        modifier = Modifier.fillMaxWidth() // Asegura que la barra se estire a lo largo de toda la pantalla
    ) {
        listOf(
            "Inicio" to Icons.Default.Home,
            "Chat" to Icons.Default.Email,
            "Perfil" to Icons.Default.Person
        ).forEachIndexed { index, (label, icon) ->
            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    when (label) {
                        "Inicio" -> if (navigator?.lastItem !is TablonScreen) navigator?.push(TablonScreen())
                        "Chat"   -> if (navigator?.lastItem !is ChatScreen) navigator?.push(ChatScreen())
                        "Perfil" -> if (navigator?.lastItem !is ProfileScreen) navigator?.push(ProfileScreen())
                    }
                }
            )
        }
    }
}