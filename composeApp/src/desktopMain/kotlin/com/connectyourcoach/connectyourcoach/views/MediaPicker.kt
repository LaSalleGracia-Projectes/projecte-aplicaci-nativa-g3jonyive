package com.connectyourcoach.connectyourcoach.views

import androidx.compose.runtime.Composable
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

actual class MediaPicker actual constructor() {
    @Composable
    actual fun OpenCamera(onImageSelected: (String) -> Unit) {
        // Simulació per a desktop - en un entorn real podries integrar amb una webcam
        onImageSelected("desktop_captured_image_${System.currentTimeMillis()}")
    }

    @Composable
    actual fun OpenGallery(onImageSelected: (String) -> Unit) {
        val fileChooser = JFileChooser().apply {
            fileFilter = FileNameExtensionFilter("Images", "jpg", "jpeg", "png")
            isMultiSelectionEnabled = false
        }

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            fileChooser.selectedFile?.absolutePath?.let { path ->
                onImageSelected(path)
            }
        }
    }
}