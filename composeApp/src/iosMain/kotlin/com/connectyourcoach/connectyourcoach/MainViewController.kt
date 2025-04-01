package com.connectyourcoach.connectyourcoach

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import com.connectyourcoach.connectyourcoach.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    // Inicialitza Koin per a dependències compartides
    initKoin()

    // Configura el tema i l'aplicació principal
    return ComposeUIViewController {
        AppiOS()
    }
}

@Composable
fun AppiOS() {
    // Configuració específica per a iOS
    App(
        onImageRequest = { type ->
            // Gestiona les sol·licituds d'imatges des del codi comú
            when (type) {
                ImageRequestType.CAMERA -> openIOSCamera()
                ImageRequestType.GALLERY -> openIOSGallery()
            }
        }
    )
}

// Funcions expect/actual per a la gestió d'imatges a iOS
internal expect fun openIOSCamera(): String?

internal expect fun openIOSGallery(): String?

enum class ImageRequestType {
    CAMERA, GALLERY
}