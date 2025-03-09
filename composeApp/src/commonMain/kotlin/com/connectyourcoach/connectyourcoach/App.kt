package com.connectyourcoach.connectyourcoach

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.connectyourcoach.connectyourcoach.nav.NavigationWrapper
import com.connectyourcoach.connectyourcoach.views.LoginView
import com.connectyourcoach.connectyourcoach.views.ProfileView
import com.connectyourcoach.connectyourcoach.views.RegisterPhotoUsernameView
import com.connectyourcoach.connectyourcoach.views.RegisterView
import com.connectyourcoach.connectyourcoach.views.RegisterViewModel
import com.connectyourcoach.connectyourcoach.views.TablonView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    MaterialTheme {
        NavigationWrapper()
    }
}
