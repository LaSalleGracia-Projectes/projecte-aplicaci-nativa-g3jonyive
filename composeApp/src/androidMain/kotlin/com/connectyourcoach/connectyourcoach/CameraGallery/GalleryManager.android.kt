package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import io.github.kashif_mehmood_km.camerak.picker.rememberGalleryPicker
import shared.SharedImage

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    val galleryPicker = rememberGalleryPicker { result ->
        result?.let {
            onResult(SharedImage(it))
        } ?: onResult(null)
    }
    return GalleryManager {
        galleryPicker.launch()
    }
}

actual class GalleryManager actual constructor(private val onLaunch: () -> Unit) {
    actual fun launch() {
        onLaunch()
    }
}
