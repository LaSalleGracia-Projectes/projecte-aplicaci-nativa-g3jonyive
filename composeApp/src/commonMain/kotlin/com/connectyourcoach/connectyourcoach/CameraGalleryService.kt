// shared/src/commonMain/kotlin/com/connectyourcoach/connectyourcoach/CameraGalleryService.kt
package com.connectyourcoach.connectyourcoach

import androidx.compose.runtime.Composable

interface CameraGalleryService {
    @Composable
    fun OpenCamera(onResult: (ByteArray?) -> Unit)

    @Composable
    fun OpenGallery(onResult: (ByteArray?) -> Unit)
    abstract fun openCamera(callback: (ByteArray?) -> Unit)
    abstract fun openGallery(callback: (ByteArray?) -> Unit)
}

expect fun createCameraGalleryService(): CameraGalleryService