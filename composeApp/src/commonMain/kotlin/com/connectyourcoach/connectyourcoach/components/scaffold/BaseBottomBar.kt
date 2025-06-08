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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.connectyourcoach.connectyourcoach.screens.ChatListScreen
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
            "Home" to Icons.Default.Home,
            "Chat" to Icons.Default.Email,
            "Profile" to Icons.Default.Person
        ).forEachIndexed { index, (label, icon) ->
            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    when (label) {
                        "Home" -> navigateToScreen(
                            navigator,
                            TablonScreen(),
                            condition = { it is TablonScreen }
                        )
                        "Chat" -> navigateToScreen(
                            navigator,
                            ChatListScreen(),
                            condition = { it is ChatListScreen }
                        )
                        "Profile" -> navigateToScreen(
                            navigator,
                            ProfileScreen(),
                            condition = { it is ProfileScreen }
                        )
                    }
                }
            )
        }
    }
}

private fun navigateToScreen(
    navigator: Navigator?,
    targetScreen: Screen,
    condition: (Any) -> Boolean
) {
    if (navigator?.lastItem == targetScreen) return
    if (navigator?.items?.any(condition) == true) {
        navigator.popUntil(condition)
        return
    }
    navigator?.push(targetScreen)
}