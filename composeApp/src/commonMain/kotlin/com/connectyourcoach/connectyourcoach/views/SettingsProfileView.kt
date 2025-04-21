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
        Text("Settings de Perfil", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
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
            text = "Edit profile",
            style = MaterialTheme.typography.h5,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
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

        Spacer(modifier = Modifier.height(16.dp))

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
                text = "Email must contain '@' and '.'",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.updatePhone(it) },
            label = { Text("Phone (optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        if (!viewModel.isValidPhone() && phone.isNotEmpty()) {
            Text(
                text = "Please enter a valid phone number",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("New password (optional)") },
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
                text = "Password must contain at least 8 characters with uppercase, lowercase, digit and special character",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.isValidSave(),
            onClick = { viewModel.onSave() }
        ) {
            if (viewModel.loading.value) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Save Changes")
            }
        }

        if (saveError != null) {
            Text(
                text = saveError!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}