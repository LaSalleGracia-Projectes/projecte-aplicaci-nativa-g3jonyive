package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import shared.SharedImage

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager


expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}