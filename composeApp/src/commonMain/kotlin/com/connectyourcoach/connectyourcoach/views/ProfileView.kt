package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    onNavigateToBlock: () -> Unit // Nou callback
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
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
            return@Column
        }

        SubcomposeAsyncImage(
            model = user?.profile_picture ?: "",
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

        Spacer(modifier = Modifier.height(16.dp))

        user?.full_name?.let {
            Text("Full Name: $it", style = MaterialTheme.typography.h6)
        } ?: Text("Full Name: Not provided", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(8.dp))

        user?.username?.let {
            Text("Username: $it", style = MaterialTheme.typography.body1)
        } ?: Text("Username: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(8.dp))

        user?.email?.let {
            Text("Email: $it", style = MaterialTheme.typography.body1)
        } ?: Text("Email: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(8.dp))

        user?.phone?.let {
            Text("Phone: ${it.ifEmpty { "Not provided" }}", style = MaterialTheme.typography.body1)
        } ?: Text("Phone: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(8.dp))

        user?.birth_date?.let { birthDate ->
            val formattedDate = try {
                val parts = birthDate.split(" ")
                val day = parts[1]
                val month = when (parts[2].lowercase()) {
                    "jan" -> "01"; "feb" -> "02"; "mar" -> "03"; "apr" -> "04"
                    "may" -> "05"; "jun" -> "06"; "jul" -> "07"; "aug" -> "08"
                    "sep" -> "09"; "oct" -> "10"; "nov" -> "11"; "dec" -> "12"
                    else -> "??"
                }
                val year = parts[3]
                "$day/$month/$year"
            } catch (e: Exception) {
                "Not provided"
            }
            Text("Birth Date: $formattedDate", style = MaterialTheme.typography.body1)
        } ?: Text("Birth Date: Not provided", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onClickLogout(onLogout) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text("Sign out", color = Color.White)
        }

        if (viewModel.isAdmin) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToBlock,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text("Go to Block Page", color = Color.White)
            }
        }
    }
}
