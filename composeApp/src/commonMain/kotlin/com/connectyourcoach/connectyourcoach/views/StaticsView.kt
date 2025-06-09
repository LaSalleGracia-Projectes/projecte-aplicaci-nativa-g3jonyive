package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.StaticsViewModel

@Composable
fun StaticsView(
    viewModel: StaticsViewModel,
    onGoToProfile: () -> Unit,
    onGoToControlPanel: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Estadística 1", "Estadística 2", "Estadística 3")

    Column(modifier = Modifier.fillMaxSize()) {

        // Botons a la part superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onGoToProfile) {
                Text("Perfil")
            }
            Button(onClick = onGoToControlPanel) {
                Text("Panell de Control")
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        // Contingut de cada tab
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (selectedTabIndex) {
                0 -> {
                    val users = viewModel.users
                    val error = viewModel.error

                    when {
                        users.isEmpty() && error == null -> {
                            Text("Carregant dades...")
                        }

                        error != null -> {
                            Text("Error: $error", color = Color.Red)
                        }

                        else -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Capçalera
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Black)
                                        .padding(8.dp)
                                ) {
                                    Text("Nom", modifier = Modifier.weight(1f))
                                    Text("Estat", modifier = Modifier.weight(1f))
                                }

                                // Cos de la taula
                                LazyColumn {
                                    items(users) { user ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(0.5.dp, Color.Gray)
                                                .padding(8.dp)
                                        ) {
                                            Text(user.username ?: "—", modifier = Modifier.weight(1f))
                                            Text(
                                                if (user.active) "Activat" else "Bloquejat",
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> Text("Contingut de l'Estadística 2", modifier = Modifier.align(Alignment.Center))
                2 -> Text("Contingut de l'Estadística 3", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
