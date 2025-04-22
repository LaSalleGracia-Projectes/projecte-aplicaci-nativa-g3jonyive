package com.connectyourcoach.connectyourcoach.auth

import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

actual class GoogleAuthHelper actual constructor() {
    private var onSuccess: ((String) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null

    actual fun signIn() {
        try {
            if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                onError?.invoke(Exception("Browser not supported"))
                return
            }

            val authUrl = buildAuthUrl()
            Desktop.getDesktop().browse(URI(authUrl))

            // Implementar lògica per capturar el codi d'autorització

        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    actual fun setCallbacks(
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onError = onError
    }

    private fun buildAuthUrl(): String {
        val clientId = "YOUR_DESKTOP_CLIENT_ID"
        val redirectUri = URLEncoder.encode("http://localhost:8080/auth/callback", StandardCharsets.UTF_8)
        val scope = URLEncoder.encode("email profile", StandardCharsets.UTF_8)

        return "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=$clientId&" +
                "redirect_uri=$redirectUri&" +
                "response_type=code&" +
                "scope=$scope&" +
                "access_type=offline&" +
                "prompt=consent"
    }
}