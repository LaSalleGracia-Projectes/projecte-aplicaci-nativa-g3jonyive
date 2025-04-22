package com.connectyourcoach.connectyourcoach

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint

actual fun createHttpClient(): HttpClient = HttpClient(CIO) {
    engine {
        // Configuració específica de Desktop
        maxConnectionsCount = 100
        endpoint {
            keepAliveTime = 30_000
            connectTimeout = 30_000
            requestTimeout = 30_000
        }
    }
}