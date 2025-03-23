package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel

@Composable
fun RegisterPhotoUsernameView(
    viewModel: RegisterViewModel,
    onRegisterComplete: () -> Unit
) {
    var username by remember { mutableStateOf(viewModel.username.value) }
    var email by remember { mutableStateOf(viewModel.email.value) }
    var password by remember { mutableStateOf(viewModel.password.value) }
    var registerError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registre", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'usuari") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contrasenya") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (registerError != null) {
            Text(registerError!!, color = MaterialTheme.colors.error)
        }

        Button(
            onClick = {
                if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                    viewModel.updateUsername(username)
                    viewModel.updateEmail(email)
                    viewModel.updatePassword(password)
                    onRegisterComplete()
                } else {
                    registerError = "Tots els camps són obligatoris"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Següent")
        }
    }
}