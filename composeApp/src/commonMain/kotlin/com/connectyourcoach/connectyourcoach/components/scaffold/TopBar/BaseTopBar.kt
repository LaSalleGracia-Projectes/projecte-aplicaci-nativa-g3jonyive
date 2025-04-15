package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val White = Color(0xFFFFFFFF)
private val Black = Color(0xFF0A0A0A)

@Composable
fun BaseTopBar(
    title: @Composable () -> Unit,
    backgroundColor: Color = White,
    actions: @Composable() (RowScope.() -> Unit) = {},
    navigationIcon: @Composable() (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    contentColor: Color = Black,
    elevation: Dp = 6.dp
) {
    var newNavigationIcon = navigationIcon
    if (onBack != null) {
        newNavigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Black)
            }
        }
    }

    TopAppBar(
        title = title,
        navigationIcon = newNavigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}
