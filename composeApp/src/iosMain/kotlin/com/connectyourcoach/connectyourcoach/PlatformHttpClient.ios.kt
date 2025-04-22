package com.connectyourcoach.connectyourcoach

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun createHttpClient(): HttpClient = HttpClient(Darwin) {
    engine {
        // Configuració específica d'iOS
        configureRequest {
            setAllowsCellularAccess(true)
            setTimeoutInterval(30.0)
        }
    }
}