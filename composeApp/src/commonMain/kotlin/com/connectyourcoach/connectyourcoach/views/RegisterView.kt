package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterPhotoUsernameView(viewModel: RegisterViewModel, onRegisterComplete: () -> Unit, onLogin: () -> Unit) {
    var username by remember { mutableStateOf(viewModel.username.value) }
    var registerError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registre", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(50.dp))

        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = "https://definicion.de/wp-content/uploads/2019/07/perfil-de-usuario.png",
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.Black, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'usuari") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (registerError != null) {
            Text(registerError!!, color = MaterialTheme.colors.error)
        }

        Button(
            onClick = {
                if (username.isNotBlank()) {
                    viewModel.updateUsername(username) // Guarda el username al ViewModel
                    onRegisterComplete()
                } else {
                    registerError = "El nom d'usuari no pot estar buit"
                }
            }
        ) {
            Text("Següent")
        }
    }
}


@Composable
fun RegisterView(viewModel: RegisterViewModel, onRegisterComplete: () -> Unit, onLogin: () -> Unit) {
    val fullname by viewModel.fullname
    val number by viewModel.phoneNumber
    val email by viewModel.email
    val password by viewModel.password
    val registerError by viewModel.registerError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(3f))

        Text(
            text = "Register",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.weight(2f))

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
            value = number,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = { Text("Phone") },
            isError = !viewModel.isValidPhoneNumber(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        if (!viewModel.isValidPhoneNumber() && number.isNotEmpty()) {
            Text(
                text = "Phone number must contain 9 digits",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

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
                    viewModel.onRegister(onRegisterComplete)
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

        Spacer(modifier = Modifier.weight(2f))

        Button(
            onClick = { viewModel.onRegister(onRegisterComplete) },
            enabled = viewModel.isValidRegister(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        if (!viewModel.isValidRegister()) {
            Text(
                text = "All fields must be filled and valid",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        if (registerError.isNotEmpty()) {
            Text(
                text = registerError,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        Text(
            text = "Already have an account?",
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onLogin() }
        )

        Spacer(modifier = Modifier.weight(3f))
    }
}
