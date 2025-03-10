package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel

@Composable
fun TablonView(viewModel: TablonViewModel, onInicio: () -> Unit, onChat: () -> Unit, onProfile: () -> Unit) {
    Scaffold(
        topBar = { TablonTopBar(viewModel) }, // Aquí solo tienes un TopBar
        bottomBar = { TablonBottomBar(onInicio, onChat, onProfile, viewModel) } // Solo la BottomBar que va en la parte inferior
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TablonSearchBar() // Esta es la SearchBar que está debajo del TopBar
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Contenido del Tablón", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
fun TablonTopBar(viewModel: TablonViewModel) {
    TopAppBar(
        title = { Text("Tablón") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            IconButton(onClick = { /* Acción del botón */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
            }
        }
    )
}

@Composable
fun TablonBottomBar(
    onInicio: () -> Unit,
    onChat: () -> Unit,
    onProfile: () -> Unit,
    viewModel: TablonViewModel
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
                        "Inicio" -> onInicio()
                        "Chat" -> onChat()
                        "Perfil" -> onProfile()
                    }
                }
            )
        }
    }
}

@Composable
fun TablonSearchBar() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        placeholder = { Text("Buscar en el tablón...") },
        singleLine = true
    )
}
