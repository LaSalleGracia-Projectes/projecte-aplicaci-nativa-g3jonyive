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
) {
    val user: User? by viewModel.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
    }
}