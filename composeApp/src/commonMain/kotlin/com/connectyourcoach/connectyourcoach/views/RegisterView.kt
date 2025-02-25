package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterPhotoUsernameView(onRegisterComplete: () -> Unit) {
    var username by remember { mutableStateOf("") }

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

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Canviar foto",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = username, onValueChange = { username = it }, label = { Text("Nom d'usuari") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegisterComplete) {
            Text("Següent")
        }
    }
}

@Composable
fun RegisterView(onRegisterComplete: () -> Unit) {
    var fullname by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var showNextScreen by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedPrefix by remember { mutableStateOf("+34") }

    val countryPrefixes = listOf("+1", "+34", "+44", "+49", "+33", "+39", "+81", "+86", "+91")

    fun isValidEmail(email: String): Boolean {
        return true
    }

    fun isValidPassword(password: String): Boolean {
        val passwordPattern = ".*(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+{}\\[\\]:;<>,.?/~`]).{8,}.*"
        return true
    }

    fun isValidPhoneNumber(number: String): Boolean {
        return number.length == 9 && number.all { it.isDigit() }
    }

    if (showNextScreen) {
        onRegisterComplete()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registre", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = fullname, onValueChange = { fullname = it }, label = { Text("Nom complet") })
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.clickable { expanded = true }) {
                    Text(selectedPrefix, modifier = Modifier.padding(16.dp))
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        countryPrefixes.forEach { prefix ->
                            DropdownMenuItem(onClick = {
                                selectedPrefix = prefix
                                expanded = false
                            }) {
                                Text(prefix)
                            }
                        }
                    }
                }
                TextField(
                    value = number,
                    onValueChange = {
                        if (it.length <= 9 && it.all { char -> char.isDigit() }) {
                            number = it
                        }
                    },
                    label = { Text("Telèfon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = !isValidPhoneNumber(number)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = !isValidEmail(it)
                },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailError
            )
            if (emailError) Text("Correu electrònic no vàlid", color = MaterialTheme.colors.error)

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !isValidPassword(it)
                },
                label = { Text("Contrasenya") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordError
            )
            if (passwordError) {
                Text("La contrasenya ha de tenir 8 caràcters, una majúscula, una minúscula, un número i un signe especial", color = MaterialTheme.colors.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showNextScreen = true },
                enabled = fullname.isNotBlank() && isValidPhoneNumber(number) && email.isNotBlank() && password.isNotBlank() && !emailError && !passwordError
            ) {
                Text("Registrar-se")
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterView() {
    RegisterPhotoUsernameView(onRegisterComplete = {})
}
