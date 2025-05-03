package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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

        if (viewModel.isLoading.value) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
            return@Column
        }

        if (viewModel.isAvatarGenerated.value) {
            KamelImage(
                resource = asyncPainterResource(data = viewModel.photoUrl.value),
                contentDescription = "User Avatar",
                modifier = Modifier.size(100.dp),
                onFailure = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        modifier = Modifier.size(100.dp),
                        contentDescription = "Error on loading image",
                    )
                },
                onLoading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.primary
                    )
                }
            )
        } else {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { viewModel.onGenerateRandomAvatar() }) {
                Text("Generate Avatar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = viewModel.fullName.value,
            onValueChange = { viewModel.updateFullName(it) },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.username.value,
            onValueChange = { viewModel.updateUsername(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.birthDate.value,
            onValueChange = { viewModel.updateBirthDate(it) },
            label = { Text("Birth date") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.phoneNumber.value,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
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
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Already have an account?",
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.End)
                .clickable { onLogin() },
            color = MaterialTheme.colors.onBackground
        )
    }
}
