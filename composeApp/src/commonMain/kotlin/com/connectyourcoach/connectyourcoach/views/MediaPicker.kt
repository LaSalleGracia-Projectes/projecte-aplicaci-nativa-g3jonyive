package com.connectyourcoach.connectyourcoach.views

import androidx.compose.runtime.Composable

expect class MediaPicker() {
    @Composable
    fun OpenCamera(onImageSelected: (String) -> Unit)

    @Composable
    fun OpenGallery(onImageSelected: (String) -> Unit)
}