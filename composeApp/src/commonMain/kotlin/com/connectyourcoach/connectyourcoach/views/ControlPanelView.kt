package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.ControlPanelViewModel

@Composable
fun ControlPanelView(
    viewModel: ControlPanelViewModel,
    onGoToProfile: () -> Unit = {},
    onGoToStatics: () -> Unit = {}
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

        Button(onClick = onGoToStatics) {
            Text(text = "Anar a les estadistiques")
        }

        when {
            users.isEmpty() && error == null -> {
                Text("Carregant usuaris...")
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
            }
            else -> {
                // Capçalera de la taula
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black)
                        .padding(8.dp)
                ) {
                    Text("ID", modifier = Modifier.weight(1f))
                    Text("Nom", modifier = Modifier.weight(1f))
                    Text("Estat", modifier = Modifier.weight(1f)) // Nom de la columna
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(users) { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.5.dp, Color.Gray)
                                .padding(8.dp)
                        ) {
                            Text(user.id?.toString() ?: "—", modifier = Modifier.weight(1f))
                            Text(user.username ?: "—", modifier = Modifier.weight(1f))
                            Button(
                                onClick = { viewModel.toggleUserActiveStatus(user) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(if (user.active) "Bloquejar" else "Activar")
                            }
                        }
                    }
                }
            }
        }
    }
}
