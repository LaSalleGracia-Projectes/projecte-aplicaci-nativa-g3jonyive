package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.connectyourcoach.connectyourcoach.models.User
import com.connectyourcoach.connectyourcoach.viewmodels.ProfileViewModel


@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    paddingValues: PaddingValues,
    onLogout: () -> Unit,
    onControlPanel: () -> Unit,
) {
    val user: User? by viewModel.user
    val email = user?.email

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
            return@Column
        }
        SubcomposeAsyncImage(
            model = user?.profile_picture ?: "",
            contentDescription = "User Avatar",
            modifier = Modifier.size(100.dp),
            loading = {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary
                )
            },
            error = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    modifier = Modifier.size(100.dp),
                    contentDescription = "Error on loading image",
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))


        user?.full_name?.let { fullName ->
            Text(
                text = "Full Name: $fullName",
                style = MaterialTheme.typography.h6
            )
        } ?: Text("Full Name: Not provided", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(8.dp))

        user?.username?.let { username ->
            Text(
                text = "Username: $username",
                style = MaterialTheme.typography.body1
            )
        } ?: Text("Username: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(8.dp))

        user?.email?.let { email ->
            Text(
                text = "Email: $email",
                style = MaterialTheme.typography.body1
            )
        } ?: Text("Email: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(8.dp))

        user?.phone?.let { phone ->
            Text(
                text = "Phone: ${phone.ifEmpty { "Not provided" }}",
                style = MaterialTheme.typography.body1
            )
        } ?: Text("Phone: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(8.dp))

        user?.birth_date?.let { birthDate ->
            val formattedDate = try {
                val parts = birthDate.split(" ")
                val day = parts[1]
                val month = when (parts[2].lowercase()) {
                    "jan" -> "01"
                    "feb" -> "02"
                    "mar" -> "03"
                    "apr" -> "04"
                    "may" -> "05"
                    "jun" -> "06"
                    "jul" -> "07"
                    "aug" -> "08"
                    "sep" -> "09"
                    "oct" -> "10"
                    "nov" -> "11"
                    "dec" -> "12"
                    else -> "??"
                }
                val year = parts[3]
                "$day/$month/$year"
            } catch (e: Exception) {
                "Not provided"
            }

            Text(
                text = "Birth Date: $formattedDate",
                style = MaterialTheme.typography.body1
            )
        } ?: Text("Birth Date: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.onClickLogout(onLogout)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text("Sign out", color = Color.White)
        }

        if  (user?.email == "admin@cyc.com") {
            Button(
                onClick = {
                    viewModel.onClickControlPanel(onControlPanel)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text("Control Panel", color = Color.Green)
            }
        }
    }
}