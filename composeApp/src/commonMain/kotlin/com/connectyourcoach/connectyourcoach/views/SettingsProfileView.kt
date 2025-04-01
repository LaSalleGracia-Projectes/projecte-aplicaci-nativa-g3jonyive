package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.apicamera.ApiCamera
import com.connectyourcoach.connectyourcoach.viewmodels.SettingsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient

@Composable
fun SettingsProfileView(viewModel: SettingsViewModel,
                        paddingValues: PaddingValues,
                        onSave: () -> Unit,
                        httpClient: HttpClient
) {
    val email by viewModel.email
    val fullname by viewModel.fullname
    val password by viewModel.password
    val saved by viewModel.saved
    val loading by viewModel.loading
    val saveError by viewModel.saveError
    val profileImageUrl by viewModel.avatarUrl

    if (saved) {
        onSave()
    }

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
        Text("Settings de Perfil", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
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

        Text(
            text = "Edit profile",
            style = MaterialTheme.typography.h5,
        )

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            value = fullname,
            onValueChange = { viewModel.updateFullname(it) },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            value = email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            isError = !viewModel.isValidEmail(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        if (!viewModel.isValidEmail() && email.isNotEmpty()) {
            Text(
                text = "Email must contain '@' and '.'",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !viewModel.isValidPassword(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.onSave()
                }
            )
        )

        if (!viewModel.isValidPassword() && password.isNotEmpty()) {
            Text(
                text = "Password must contain at least 8 characters, one uppercase, one lowercase, one digit and one special character",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.isValidSave(),
            onClick = {
                viewModel.onSave()
            }
        ) {
            Text("Save")
        }

        if (saveError != null) {
            Text(
                text = saveError!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}