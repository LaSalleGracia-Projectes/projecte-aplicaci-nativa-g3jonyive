package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.connectyourcoach.connectyourcoach.viewmodels.SettingsViewModel

@Composable
fun SettingsProfileView(viewModel: SettingsViewModel, paddingValues: PaddingValues, onSave: () -> Unit) {
    val email by viewModel.email
    val fullname by viewModel.fullname
    val password by viewModel.password
    val saved by viewModel.saved
    val loading by viewModel.loading
    val saveError by viewModel.saveError

    if (saved) {
        onSave()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            return@Column
        }

        Spacer(modifier = Modifier.weight(3f))

        Text(
            text = "Edit profile",
            style = MaterialTheme.typography.h5,
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
            label = { Text("New Password") },
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
                    viewModel.onSave()
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
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.isValidSave(),
            onClick = {
                viewModel.onSave()
            }
        ) {
            Text("Desar")
        }

        if (saveError != null) {
            Text(
                text = saveError!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(3f))
    }
}