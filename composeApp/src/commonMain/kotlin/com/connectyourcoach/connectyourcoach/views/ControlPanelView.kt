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
        Text(text = "Welcome to Control Panel, admin!")

        Button(onClick = onGoToProfile) {
            Text(text = "Go to Profile")
        }

        Button(onClick = onGoToStatics) {
            Text(text = "Go to Statics")
        }

        when {
            users.isEmpty() && error == null -> {
                Text("Charging users...")
            }
            error != null -> {
                Text("Error: $error", color = Color.Red)
            }
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black)
                        .padding(8.dp)
                ) {
                    Text("Name", modifier = Modifier.weight(1f))
                    Text("State", modifier = Modifier.weight(1f))
                    Text("Ban", modifier = Modifier.weight(1f))
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
                            Text(user.username ?: "—", modifier = Modifier.weight(1f))
                            Button(
                                onClick = { viewModel.toggleUserActiveStatus(user) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(if (user.active) "Block" else "Activate")
                            }
                            Button(
                                onClick = { viewModel.deleteUser(user) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Ban")
                            }
                        }
                    }
                }
            }
        }
    }
}
