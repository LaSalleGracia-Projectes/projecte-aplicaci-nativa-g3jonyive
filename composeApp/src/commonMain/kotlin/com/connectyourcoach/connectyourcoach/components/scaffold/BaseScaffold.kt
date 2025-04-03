package com.connectyourcoach.connectyourcoach.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun BaseScaffold(navigator: Navigator?, showBottomBar: Boolean = true, topBar: @Composable () -> Unit, content: @Composable (paddingValues: PaddingValues) -> Unit) {
    Scaffold(
        topBar = topBar, // Aquí solo tienes un TopBar
        bottomBar = { if (showBottomBar) BaseBottomBar(navigator) } // Solo la BottomBar que va en la parte inferior
    ) { paddingValues ->
        content(paddingValues)
    }
}