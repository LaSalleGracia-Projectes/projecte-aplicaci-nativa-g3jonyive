package com.connectyourcoach.connectyourcoach

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit

actual fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    engine {
        // Configuració específica d'Android
        config {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // Configuració addicional opcional
            retryOnConnectionFailure(true)
            followRedirects(true)
            followSslRedirects(true)
        }
    }

    // Configuració comuna de Ktor
    expectSuccess = true
}