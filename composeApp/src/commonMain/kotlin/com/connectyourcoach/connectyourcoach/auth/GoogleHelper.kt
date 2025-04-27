package com.connectyourcoach.connectyourcoach.auth

import androidx.compose.runtime.Composable

expect class GoogleAuthHelper() {
    fun launchSignIn(onResult: (idToken: String?) -> Unit)

    @Composable
    fun LaunchSignIn(onResult: (idToken: String?) -> Unit)
}
