package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.connectyourcoach.connectyourcoach.viewmodels.ProfileViewModel

@Composable
fun ProfileView(viewModel: ProfileViewModel, paddingValues: PaddingValues, onLogout: () -> Unit) {
    val fullname by viewModel.fullname
    val email by viewModel.email
    val phoneNumber by viewModel.phoneNumber
    val photoUrl by viewModel.photoUrl

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = photoUrl, contentDescription = "Profile picture", modifier = Modifier.size(128.dp).clip(MaterialTheme.shapes.small))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Full Name: $fullname")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Email: $email")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Phone: $phoneNumber")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.onLogout(); onLogout() }, modifier = Modifier.fillMaxWidth()) {
            Text("Sign out")
        }
    }
}
