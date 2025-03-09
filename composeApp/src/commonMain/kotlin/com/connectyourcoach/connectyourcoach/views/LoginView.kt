package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.connectyourcoach.connectyourcoach.viewmodels.LoginViewModel
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginView(viewModel: LoginViewModel, onLogin: () -> Unit, onRegister: () -> Unit) {
    val username by viewModel.username
    val password by viewModel.password
    val loading by viewModel.loading
    val error by viewModel.error

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

        Spacer(modifier = Modifier.weight(10f))
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(256.dp)
                .background(Color.Transparent)
        )
        Spacer(modifier = Modifier.weight(3f))
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }
        TextField(
            value = username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        TextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    val status = viewModel.onLogin()
                    if (status) {
                        onLogin()
                    }
                }
            )
        )
        Spacer(modifier = Modifier.weight(3f))
        Button(
            onClick = {
                val status = viewModel.onLogin()
                if (status) {
                    onLogin()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Text(
            text = "Don't have an account?",
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onRegister() },
        )
        Spacer(modifier = Modifier.weight(10f))
    }
}