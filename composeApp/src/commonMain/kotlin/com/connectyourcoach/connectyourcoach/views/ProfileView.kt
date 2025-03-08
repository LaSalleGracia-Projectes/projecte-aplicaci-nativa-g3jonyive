package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ProfileView(onNavigateToSettings: () -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botó de configuració i logout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onLogout) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Tancar sessió", tint = Color.Red)
            }
            IconButton(onClick = onNavigateToSettings) {
                Icon(Icons.Default.Settings, contentDescription = "Configuració")
            }
        }

        // Títol del perfil
        Text(
            text = "Perfil",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )

        // Foto de perfil amb botó d'edició
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = "https://definicion.de/wp-content/uploads/2019/07/perfil-de-usuario.png",
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.Black, CircleShape)
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Canviar foto",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "AuraTurqesa", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(32.dp))

        // Informació de l'usuari
        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileRow(Icons.Default.AccountCircle, "Sergi Saravia Terricabras")
            ProfileRow(Icons.Default.Email, "sergi.saravia@gracia.lasalle.cat")
            ProfileRow(Icons.Default.Phone, "(+34) 634 534 089")
            ProfileRow(Icons.Default.DateRange, "11/03/2003")
        }
    }
}

@Composable
fun ProfileRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colors.primary)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}
