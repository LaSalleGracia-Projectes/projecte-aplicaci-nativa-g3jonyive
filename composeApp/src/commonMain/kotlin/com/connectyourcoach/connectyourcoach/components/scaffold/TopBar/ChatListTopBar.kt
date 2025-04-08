package com.connectyourcoach.connectyourcoach.components.scaffold.TopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.box_white
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatListTopBar() {
    TopAppBar(
        title = { Text("Chat") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        actions = {
            Image(
                painter = painterResource(Res.drawable.box_white),
                contentDescription = "Archivar chat",
                modifier = Modifier
                    .size(36.dp)
                    .clickable {

                    }
                    .padding(4.dp)
            )
        }
    )
}