package com.connectyourcoach.connectyourcoach

import androidx.compose.runtime.Composable

class CameraGalleryController(val service: CameraGalleryService) {
    @Composable
    fun openCamera(callback: (ByteArray?) -> Unit) {
        service.openCamera(callback)
    }

    @Composable
    fun openGallery(callback: (ByteArray?) -> Unit) {
        service.openGallery(callback)
    }
}