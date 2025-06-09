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
    val tabTitles = listOf("User States", "Num of Posts per User", "Top 3 high profits")

    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onGoToProfile) {
                Text("Profile")
            }
            Button(onClick = onGoToControlPanel) {
                Text("Control Panel")
            }
        }

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
                            Text("Charging users...")
                        }

                        error != null -> {
                            Text("Error: $error", color = Color.Red)
                        }

                        else -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Black)
                                        .padding(8.dp)
                                ) {
                                    Text("Name", modifier = Modifier.weight(1f))
                                    Text("State", modifier = Modifier.weight(1f))
                                }

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
                                                if (user.active) "Active" else "Blocked",
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    val postCounts = viewModel.userPostCounts
                    val error = viewModel.postCountError

                    LaunchedEffect(viewModel.users) {
                        if (postCounts.isEmpty() && error == null && viewModel.users.isNotEmpty()) {
                            viewModel.loadUserPostCounts()
                        }
                    }

                    when {
                        postCounts.isEmpty() && error == null -> {
                            Text("Charging statics...", modifier = Modifier.align(Alignment.Center))
                        }

                        error != null -> {
                            Text("Error: $error", color = Color.Red, modifier = Modifier.align(Alignment.Center))
                        }

                        else -> {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(1.dp, Color.Black)
                                        .padding(8.dp)
                                ) {
                                    Text("Name", modifier = Modifier.weight(1f))
                                    Text("Number of Posts", modifier = Modifier.weight(1f))
                                }

                                LazyColumn {
                                    items(postCounts) { (username, count) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(0.5.dp, Color.Gray)
                                                .padding(8.dp)
                                        ) {
                                            Text(username, modifier = Modifier.weight(1f))
                                            Text(count.toString(), modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> Text("Contingut de l'Estadística 3", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
