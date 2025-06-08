package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.ControlPanelViewModel


@Composable
fun ControlPanelView(
    viewModel: ControlPanelViewModel, // <- aquí el canvi correcte
    onGoToProfile: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val users = viewModel.users
    val error = viewModel.error

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Benvingut al panell de control")

        Button(onClick = onGoToProfile) {
            Text(text = "Anar al perfil")
        }

        Button(onClick = onLogout) {
            Text(text = "Tancar sessió")
        }

        when {
            users.isEmpty() && error == null -> {
                Text("Carregant usuaris...")
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
            }
            else -> {
                users.forEach { user ->
                    Text("Usuari: ${user.username}")
                }
            }
        }
    }
}
