package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import connectyourcoach.composeapp.generated.resources.logo
import connectyourcoach.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginView() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Login Logo",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 256.dp)
            )
            Spacer(modifier = Modifier.weight(0.5f))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 256.dp)
                    .padding(top = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 512.dp)
                    .padding(top = 32.dp)
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.weight(2f))
            Text("Si no tienes cuenta:")
            Button(onClick = {
            }) {
                Text("REGISTRO")
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Text(text = message, color = MaterialTheme.colors.primary)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
