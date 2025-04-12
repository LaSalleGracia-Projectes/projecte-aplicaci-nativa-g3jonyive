package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable

@Composable
actual fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager {
    // A Desktop no necessitem gestionar la galeria, així que només retornem un objecte de GalleryManager sense funcionalitat
    return GalleryManager {
        // En Desktop no es farà res quan es llança la galeria
    }
}

actual class GalleryManager actual constructor(onLaunch: () -> Unit) {
    actual fun launch() {
        // En Desktop no es farà res en aquest mètode
    }
}
