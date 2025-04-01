package com.connectyourcoach.connectyourcoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Alignment
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        setContent {
            // Obtenim el client HTTP directament (sense try-catch)
            val httpClient = org.koin.compose.koinInject<HttpClient>()
            App(httpClient = httpClient)
        }
    }
}

val appModule = module {
    single { createHttpClient() }
}