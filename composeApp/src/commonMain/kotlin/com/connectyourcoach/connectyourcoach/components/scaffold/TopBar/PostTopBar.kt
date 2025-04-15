package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PostTopBar(title: String, onBack: () -> Unit) {
    BaseTopBar(
        title = { Text(title) },
        onBack = onBack
    )
}