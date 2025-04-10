package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import io.github.kashif_mehmood_km.camerak.CameraKConfig
import io.github.kashif_mehmood_km.camerak.CameraKResult
import io.github.kashif_mehmood_km.camerak.rememberCameraK
import shared.SharedImage

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val cameraK = rememberCameraK(
        config = CameraKConfig(),
        onResult = { result ->
            when (result) {
                is CameraKResult.Success -> {
                    onResult(SharedImage(result.data))
                }
                is CameraKResult.Failure -> {
                    onResult(null)
                }
            }
        }
    )
    return CameraManager {
        cameraK.launch()
    }
}

actual class CameraManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}
