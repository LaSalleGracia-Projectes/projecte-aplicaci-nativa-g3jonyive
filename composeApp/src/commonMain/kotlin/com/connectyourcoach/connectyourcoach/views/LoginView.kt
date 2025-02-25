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
import connectyourcoach.composeapp.generated.resources.Logo
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
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.Logo),
                    contentDescription = "Login Logo",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 256.dp)
            )
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
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 512.dp)
                    .padding(top = 32.dp)
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message, color = MaterialTheme.colors.primary)
        }
    }
}
