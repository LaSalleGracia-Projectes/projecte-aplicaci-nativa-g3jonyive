package com.connectyourcoach.connectyourcoach

import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import org.koin.compose.KoinApplication
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController {
    KoinApplication(application = {
        modules(module {
            single {
                HttpClient(Darwin) {
                    // Configuració del timeout directament al client
                    expectSuccess = true
                    engine {
                        // Configuració específica per a iOS (Darwin)
                        configureRequest {
                            setAllowsCellularAccess(true)
                            setTimeoutInterval(30.0)
                        }
                    }
                }
            }
        })
    }) {
        val httpClient = org.koin.compose.koinInject<HttpClient>()
        App(httpClient = httpClient)
    }
}