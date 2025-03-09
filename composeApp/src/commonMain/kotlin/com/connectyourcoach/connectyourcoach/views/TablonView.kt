package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
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
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

@Composable
fun TablonView(viewModel: TablonViewModel, onSignOut: () -> Unit) {
    Scaffold(
        topBar = { TablonTopBar(viewModel, onSignOut) },
        bottomBar = { TablonBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TablonSearchBar()
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
fun TablonTopBar(viewModel: TablonViewModel, onSignOut: () -> Unit) {
    TopAppBar(
        title = { Text("Tablón") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            IconButton(onClick = { /* Acción del botón */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
            }
            IconButton(onClick = {
                viewModel.signOut()
                onSignOut()
            }) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar sesión")
            }
        }
    )
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

@Composable
fun TablonBottomBar() {
    var selectedItem by remember { mutableStateOf(0) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        listOf("Inicio" to Icons.Default.Home, "Chat" to Icons.Default.Email, "Perfil" to Icons.Default.Person).forEachIndexed { index, (label, icon) ->
            BottomNavigationItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

