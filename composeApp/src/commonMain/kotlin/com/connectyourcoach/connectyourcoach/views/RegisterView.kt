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
    httpClient: HttpClient,
    onLogin: () -> Boolean?
) {
    LaunchedEffect(Unit) {
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
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.photoUrl.value.isNotBlank()) {
            KamelImage(
                resource = asyncPainterResource(data = viewModel.photoUrl.value),
                contentDescription = "Avatar de l'usuari",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { viewModel.generateRandomAvatar() }) {
                    Text("Generar Nou Avatar")
                }
            }
        } else {
            Button(onClick = { viewModel.showAvatarGenerator(true) }) {
                Text("Seleccionar Avatar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = viewModel.username.value,
            onValueChange = { viewModel.updateUsername(it) },
            label = { Text("Nom d'usuari") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Contrasenya") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.registerError.value.isNotBlank()) {
            Text(viewModel.registerError.value, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
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
