package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator

@Composable
fun AddChatTopBar() {
    val navigator = LocalNavigator.current

    BaseTopBar(
        title = { Text("Add chat") },
        onBack = { navigator?.pop() },
    )
}
