package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    // A Desktop no necessitem gestionar la càmera, així que només retornem un objecte de CameraManager sense funcionalitat
    return CameraManager {
        // En Desktop no es farà res quan es llança la càmera
    }
}

actual class CameraManager actual constructor(onLaunch: () -> Unit) {
    actual fun launch() {
        // En Desktop no es farà res en aquest mètode
    }
}
