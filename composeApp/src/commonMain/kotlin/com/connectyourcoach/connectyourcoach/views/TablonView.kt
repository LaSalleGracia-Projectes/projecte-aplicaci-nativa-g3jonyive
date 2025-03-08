package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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

@Composable
fun TablonView() {
    Scaffold(
        topBar = { TablonTopBar() },
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
fun TablonTopBar() {
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

