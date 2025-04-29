package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.apicamera.ApiCamera
import com.connectyourcoach.connectyourcoach.viewmodels.SettingsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient

@Composable
fun SettingsProfileView(
    viewModel: SettingsViewModel,
    paddingValues: PaddingValues,
    onSave: () -> Unit,
    httpClient: HttpClient
) {
    val email by viewModel.email
    val fullname by viewModel.fullname
    val phone by viewModel.phone
    val password by viewModel.password
    val saved by viewModel.saved
    val loading by viewModel.loading
    val saveError by viewModel.saveError
    val profileImageUrl by viewModel.photoUrl

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
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Configuració del Perfil", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(50.dp))

        // Secció d'avatar
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (viewModel.photoUrl.value.isNotBlank()) {
                KamelImage(
                    resource = asyncPainterResource(data = viewModel.photoUrl.value),
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
            text = "Editar perfil",
            style = MaterialTheme.typography.h5,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Camp Nom complet
        OutlinedTextField(
            value = fullname,
            onValueChange = { viewModel.updateFullname(it) },
            label = { Text("Nom complet") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Camp Email
        OutlinedTextField(
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
                text = "L'email ha de contenir '@' i '.'",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Camp Telèfon
        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.updatePhone(it) },
            label = { Text("Telèfon (opcional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        if (!viewModel.isValidPhone() && phone.isNotEmpty()) {
            Text(
                text = "Si us plau, introdueix un número de telèfon vàlid",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Camp Contrasenya
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Nova contrasenya (opcional)") },
            visualTransformation = PasswordVisualTransformation(),
            isError = password.isNotEmpty() && !viewModel.isValidPassword(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.onSave() }
            )
        )

        if (password.isNotEmpty() && !viewModel.isValidPassword()) {
            Text(
                text = "La contrasenya ha de contenir com a mínim 8 caràcters amb majúscules, minúscules, números i caràcters especials",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botó Guardar
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.isValidSave(),
            onClick = { viewModel.onSave() }
        ) {
            if (viewModel.loading.value) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Guardar Canvis")
            }
        }

        if (viewModel.saveError.value != null) {
            Text(
                text = viewModel.saveError.value!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}