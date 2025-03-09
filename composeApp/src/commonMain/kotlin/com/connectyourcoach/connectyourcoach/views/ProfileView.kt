package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileView(viewModel: RegisterViewModel, onNavigateToSettings: () -> Unit, onLogout: () -> Unit, navigateToInicio: () -> Unit) {
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
        bottomBar = { ProfileBottomBar(navigateToInicio) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nom complet: ${viewModel.fullname}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Correu electrònic: ${viewModel.email}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Telèfon: ${viewModel.phoneNumber}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tancar sessió")
            }
        }
    }
}

@Composable
fun ProfileBottomBar(navigateToInicio: () -> Unit) {
    var selectedItem by remember { mutableStateOf(2) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
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
                    if (label == "Inicio") navigateToInicio()
                }
            )
        }
    }
}