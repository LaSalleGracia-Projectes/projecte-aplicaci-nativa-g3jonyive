package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.ProfileViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import com.connectyourcoach.connectyourcoach.viewmodels.TablonViewModel

@Composable
fun ProfileView(
    viewModel: ProfileViewModel,
    paddingValues: PaddingValues,
    onLogout: () -> Unit
) {
    val fullname = viewModel.fullName
    val email = viewModel.email
    val phoneNumber = viewModel.phoneNumber

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Comprovem que les dades del viewModel es mostren
        Text("Nom complet: ${fullname}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Correu electrònic: ${email}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Telèfon: ${phoneNumber}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.onLogout()
                onLogout()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tancar sessió")
        }
    }
}
