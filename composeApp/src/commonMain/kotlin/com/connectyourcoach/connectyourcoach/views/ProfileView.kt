package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileView(viewModel: RegisterViewModel, onNavigateToSettings: () -> Unit, onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de l'Usuari") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuració")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Comprovem que les dades del viewModel es mostren
                Text("Nom complet: ${viewModel.fullname}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Correu electrònic: ${viewModel.email}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Telèfon: ${viewModel.phoneNumber}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Quan es tanca sessió, es pot restablir el viewModel
                        onLogout()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tancar sessió")
                }
                // Botó per actualitzar manualment les dades del perfil
                Button(
                    onClick = {
                        // Actualitza les dades amb noves informacions, per exemple
                        viewModel.updateUserDetails("Nou Nom Actualitzat", "987654321")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Actualitzar informació")
                }
            }
        }
    )
}
