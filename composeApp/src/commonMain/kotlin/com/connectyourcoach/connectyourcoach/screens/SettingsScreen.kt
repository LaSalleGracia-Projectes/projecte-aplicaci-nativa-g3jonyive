package com.connectyourcoach.connectyourcoach.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.connectyourcoach.connectyourcoach.components.scaffold.BaseScaffold
import com.connectyourcoach.connectyourcoach.components.scaffold.TopBar.SettingsTopBar
import com.connectyourcoach.connectyourcoach.viewmodels.SettingsViewModel
import com.connectyourcoach.connectyourcoach.views.SettingsProfileView
import io.ktor.client.HttpClient

class SettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel by remember { mutableStateOf(SettingsViewModel()) }
        val httpClient = remember { HttpClient() }

        BaseScaffold(
            navigator = navigator,
            topBar = {
                SettingsTopBar(
                    onBack = { navigator?.pop() },
                )
            },
        ) { paddingValues ->
            SettingsProfileView(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onSave = {
                    navigator?.pop()
                },
                httpClient = httpClient
            )
        }
    }
}