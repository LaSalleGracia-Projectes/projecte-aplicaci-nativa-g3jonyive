package com.connectyourcoach.connectyourcoach

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ConnectYourCoach"
    ) {
        App(httpClient = createHttpClient())
    }
}