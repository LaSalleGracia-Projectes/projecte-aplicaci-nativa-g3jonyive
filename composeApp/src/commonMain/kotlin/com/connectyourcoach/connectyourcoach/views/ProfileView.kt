package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.ProfileViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel

@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    onInicio: () -> Unit,
    onChat: () -> Unit,
    onProfile: () -> Unit
) {
    val fullname = viewModel.fullName
    val email = viewModel.email
    val phoneNumber = viewModel.phoneNumber

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
        bottomBar = {
            TablonBottomBar(onInicio, onChat, onProfile, TablonViewModel()) // Aquí afegim la BottomBar
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Comprovem que les dades del viewModel es mostren
                Text("Nom complet: ${fullname}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Correu electrònic: ${email}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Telèfon: ${phoneNumber}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.onLogout()
                        onLogout()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tancar sessió")
                }
                // Botó per actualitzar manualment les dades del perfil
                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Actualitzar informació")
                }
            }
        }
    )
}
