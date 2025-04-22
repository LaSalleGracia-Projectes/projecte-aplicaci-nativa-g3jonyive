package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun SettingsTopBar(onBack: () -> Unit) {
    BaseTopBar(
        title = { Text("Settings") },
        onBack = onBack
    )
}