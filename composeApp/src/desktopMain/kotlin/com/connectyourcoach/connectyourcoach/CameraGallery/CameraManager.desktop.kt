package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import shared.SharedImage

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    TODO("Not yet implemented")
}

actual class CameraManager actual constructor(onLaunch: () -> Unit) {
    actual fun launch() {
    }
}