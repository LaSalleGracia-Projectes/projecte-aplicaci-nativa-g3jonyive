package com.connectyourcoach.connectyourcoach.views

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.MainActivity
import com.connectyourcoach.connectyourcoach.viewmodels.LoginViewModel
import connectyourcoach.composeapp.generated.resources.Res
import connectyourcoach.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginView(
    viewModel: LoginViewModel,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onGoogleLogin: (String) -> Unit
) {
    val email by viewModel.email
    val password by viewModel.password
    val loading by viewModel.loading
    val error by viewModel.error
    val loggedIn by viewModel.loggedIn

    // Si l'usuari ja està connectat, anem a la pantalla principal
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
                imeAction = ImeAction.Next
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
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { viewModel.onLogin() }
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.onLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !loading
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botó per login amb Google
        Button(
            onClick = {
                val context = LocalContext.current
                if (context is Activity) {
                    val helper = MainActivity.googleAuthHelper
                    helper.setActivity(context)
                    helper.setCallbacks(
                        onSuccess = { idToken ->
                            if (idToken != null) {
                                onGoogleLogin(idToken)
                            } else {
                                viewModel.error.value = "Google login failed"
                            }
                        },
                        onError = {
                            viewModel.error.value = "Google login error"
                        }
                    )
                    helper.signIn()
                } else {
                    viewModel.error.value = "Invalid context"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            Text("Login with Google")
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