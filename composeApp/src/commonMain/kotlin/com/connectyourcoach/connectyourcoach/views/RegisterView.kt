package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel

@Composable
fun RegisterView(
    viewModel: RegisterViewModel,
    onRegisterComplete: () -> Unit,
    onLogin: () -> Unit,
) {
    val isActive by viewModel.active
    var showBlockedAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registre", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.isLoading.value) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
            return@Column
        }

        if (viewModel.isAvatarGenerated.value) {
            SubcomposeAsyncImage(
                model = viewModel.photoUrl.value,
                contentDescription = "User Avatar",
                modifier = Modifier.size(100.dp),
                loading = { CircularProgressIndicator(color = MaterialTheme.colors.primary) },
                error = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        modifier = Modifier.size(100.dp),
                        contentDescription = "Error on loading image",
                    )
                }
            )
        } else {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (viewModel.fullNameError.value.isNotBlank()) {
            Text(text = viewModel.fullNameError.value, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.username.value,
            onValueChange = { viewModel.updateUsername(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (viewModel.usernameError.value.isNotBlank()) {
            Text(text = viewModel.usernameError.value, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.birthDate.value,
            onValueChange = { viewModel.updateBirthDate(it) },
            label = { Text("Birth date") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (viewModel.birthDateError.value.isNotBlank()) {
            Text(text = viewModel.birthDateError.value, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.phoneNumber.value,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                autoCorrect = false,
                imeAction = ImeAction.Next
            )
        )
        if (viewModel.phoneNumberError.value.isNotBlank()) {
            Text(text = viewModel.phoneNumberError.value, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                autoCorrect = false,
                imeAction = ImeAction.Next
            )
        )
        if (viewModel.emailError.value.isNotBlank()) {
            Text(text = viewModel.emailError.value, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (viewModel.passwordError.value.isNotBlank()) {
            Text(text = viewModel.passwordError.value, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.registerError.value.isNotBlank()) {
            Text(text = viewModel.registerError.value, color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (isActive) {
                    viewModel.onRegister()
                } else {
                    showBlockedAlert = true
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
                .clickable {
                    if (isActive) {
                        onLogin()
                    } else {
                        showBlockedAlert = true
                    }
                },
            color = MaterialTheme.colors.onBackground
        )

        if (viewModel.showDialog.value) {
            AlertDialog(
                title = { Text("Register complete") },
                text = { Text("You have been registered successfully") },
                onDismissRequest = { viewModel.onDismissDialog() },
                confirmButton = {
                    Button(onClick = {
                        viewModel.onDismissDialog()
                        onRegisterComplete()
                    }) {
                        Text("Ok!")
                    }
                },
            )
        }

        if (showBlockedAlert) {
            AlertDialog(
                onDismissRequest = { showBlockedAlert = false },
                title = { Text("Blocked") },
                text = { Text("Sorry, you are blocked!") },
                confirmButton = {
                    Button(onClick = { showBlockedAlert = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
