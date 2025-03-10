package com.connectyourcoach.connectyourcoach.components.scaffold

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable

@Composable
fun ProfileTopBar(onNavigateToSettings: () -> Unit) {
    TopAppBar(
        title = { Text("Perfil de l'Usuari") },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Configuració")
            }
        }
    )
}