package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@Composable
fun AddCardTopBar(onBackClick: () -> Unit) {
    BaseTopBar(title = {Text( "Nuevo Post")},
        onBack = onBackClick
    )
}
