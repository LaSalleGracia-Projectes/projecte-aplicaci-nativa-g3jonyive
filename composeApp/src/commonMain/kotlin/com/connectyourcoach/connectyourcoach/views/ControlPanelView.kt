package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.ControlPanelViewModel

@Composable
fun ControlPanelView(
    viewModel: ControlPanelViewModel,
    paddingValues: PaddingValues,
    onLogout: () -> Unit,
    onGoToProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Benvingut al panell de control")

        Button(onClick = onGoToProfile) {
            Text("Anar al perfil")
        }

        Button(onClick = onLogout) {
            Text("Tancar sessió")
        }
    }
}
