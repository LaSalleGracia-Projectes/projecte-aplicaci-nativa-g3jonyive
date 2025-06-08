package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.LoginViewModel
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginView(
    viewModel: LoginViewModel,
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {
    val email by viewModel.email
    val password by viewModel.password
    val loading by viewModel.loading
    val error by viewModel.error
    val loggedIn by viewModel.loggedIn
    val isBlocked = !viewModel.active.value

    if (loggedIn) {
        onLogin()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
            return@Column
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(256.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        TextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                autoCorrect = false
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.onLogin() }
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { if (!isBlocked) viewModel.onLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !loading && !isBlocked
        ) {
            Text("Login")
        }
        if (isBlocked) {
            AlertDialog(
                onDismissRequest = {},
                title = { Text("Compte bloquejat") },
                text = { Text("Has sigut bloquejat. Contacta amb l'administrador.") },
                confirmButton = {
                    Button(onClick = {}) { Text("OK") }
                }
            )
        }
        Column {
            Text(
                text = "Don't have an account?",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onRegister() },
                color = MaterialTheme.colors.onBackground
            )

            Text(
                text = "Forgot password?",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { viewModel.onForgotPassword() },
                color = MaterialTheme.colors.onBackground
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}