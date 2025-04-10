package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import shared.SharedImage

@Composable
expect fun rememberGalleryManager(onResult: (SharedImage?) -> Unit): GalleryManager


expect class GalleryManager(
    onLaunch: () -> Unit
) {
    fun launch()
}