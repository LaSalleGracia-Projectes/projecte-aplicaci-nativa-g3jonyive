package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.apicamera.ApiCamera
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient

@Composable
fun RegisterPhotoUsernameView(
    viewModel: RegisterViewModel,
    onRegisterComplete: () -> Unit,
    httpClient: HttpClient
) {
    var username by remember { mutableStateOf(viewModel.username.value) }
    var email by remember { mutableStateOf(viewModel.email.value) }
    var password by remember { mutableStateOf(viewModel.password.value) }

    LaunchedEffect(httpClient) {
        viewModel.initialize(httpClient)
    }

    if (viewModel.showAvatarGenerator.value) {
        ApiCamera(httpClient) { imageUrl ->
            viewModel.updateAvatarUrl(imageUrl)
            viewModel.showAvatarGenerator(false)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registre", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(50.dp))

        // Secció d'avatar
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (viewModel.avatarUrl.value.isNotBlank()) {
                KamelImage(
                    resource = asyncPainterResource(data = viewModel.avatarUrl.value),
                    contentDescription = "Avatar de l'usuari",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.generateRandomAvatar() },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Generar Nou Avatar")
                }
            } else {
                Button(
                    onClick = { viewModel.showAvatarGenerator(true) },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Seleccionar Avatar")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        if (viewModel.registerError.value.isNotBlank()) {
            Text(viewModel.registerError.value, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                viewModel.updateUsername(username)
                viewModel.updateEmail(email)
                viewModel.updatePassword(password)

                if (viewModel.isValidRegister()) {
                    viewModel.onRegister(onRegisterComplete)
                } else {
                    viewModel.updateRegisterError("Si us plau, omple tots els camps correctament i selecciona un avatar")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar-se")
        }
    }
}