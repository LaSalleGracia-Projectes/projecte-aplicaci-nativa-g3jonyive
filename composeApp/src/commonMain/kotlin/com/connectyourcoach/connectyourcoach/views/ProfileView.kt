package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel

@Composable
fun ProfileView(
    viewModel: RegisterViewModel,
    onNavigateToSettings: () -> Unit,
    onLogout: () -> Unit,
    onInicio: () -> Unit,
    onChat: () -> Unit,
    onProfile: () -> Unit
) {
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
            TablonBottomBar(onInicio, onChat, onProfile, TablonViewModel())
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Nom complet: ${viewModel.fullname.value}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Nom d'usuari: ${viewModel.username.value}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Correu electrònic: ${viewModel.email.value}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Telèfon: ${viewModel.phoneNumber.value}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tancar sessió")
                }
            }
        }
    )
}
