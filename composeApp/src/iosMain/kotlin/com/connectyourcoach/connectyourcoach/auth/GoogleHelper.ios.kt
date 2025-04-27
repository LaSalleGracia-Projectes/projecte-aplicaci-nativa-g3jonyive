package com.connectyourcoach.connectyourcoach.auth

import androidx.compose.runtime.Composable

actual class GoogleAuthHelper actual constructor() {
    actual fun launchSignIn(onResult: (idToken: String?) -> Unit) {
    }

    @Composable
    actual fun LaunchSignIn(onResult: (idToken: String?) -> Unit) {
    }

}