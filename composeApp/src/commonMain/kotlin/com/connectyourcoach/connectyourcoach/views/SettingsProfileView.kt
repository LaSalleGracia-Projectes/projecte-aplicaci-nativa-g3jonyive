package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun SettingsProfileView(onBack: () -> Unit) {
    var fullName by remember { mutableStateOf("Sergi Saravia Terricabras") }
    var username by remember { mutableStateOf("AuraTurqesa") }
    var email by remember { mutableStateOf("sergi.saravia@gracia.lasalle.cat") }
    var birthdate by remember { mutableStateOf("11/03/2003") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start).padding(16.dp)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Tornar")
        }

        Text("Editar Perfil", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(50.dp))

        TextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Nom complet") })
        Spacer(modifier = Modifier.height(28.dp))

        TextField(value = username, onValueChange = { username = it }, label = { Text("Nom d'usuari") })
        Spacer(modifier = Modifier.height(28.dp))

        TextField(value = email, onValueChange = { email = it }, label = { Text("Correu electrònic") })
        Spacer(modifier = Modifier.height(28.dp))

        TextField(value = birthdate, onValueChange = { birthdate = it }, label = { Text("Data de naixement") })
        Spacer(modifier = Modifier.height(36.dp))

        Button(onClick = onBack) {
            Text("Desar i tornar")
        }
    }
}
