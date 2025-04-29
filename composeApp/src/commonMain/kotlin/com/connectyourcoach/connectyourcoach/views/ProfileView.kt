package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.viewmodels.ProfileViewModel

@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    paddingValues: PaddingValues,
    onLogout: () -> Unit,
    onSettingsClick: () -> Unit
) {
    // Carregar dades de l'usuari
    LaunchedEffect(Unit) {
        viewModel.refreshUserData()
    }

    val fullname by viewModel.fullname
    val email by viewModel.email
    val phoneNumber by viewModel.phoneNumber
    val photoUrl by viewModel.photoUrl

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto de perfil
        AsyncImage(
            model = viewModel.photoUrl.value,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(128.dp)
                .clip(MaterialTheme.shapes.small)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Informació de l'usuari
        Text("Nom complet: $fullname", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Email: $email", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Telèfon: ${viewModel.phoneNumber.value.ifEmpty { "No proporcionat" }}",
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botó de configuració
        Button(
            onClick = onSettingsClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text("Configuració")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botó de tancar sessió
        Button(
            onClick = {
                viewModel.onLogout()
                onLogout()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text("Tancar sessió", color = Color.White)
        }
    }
}