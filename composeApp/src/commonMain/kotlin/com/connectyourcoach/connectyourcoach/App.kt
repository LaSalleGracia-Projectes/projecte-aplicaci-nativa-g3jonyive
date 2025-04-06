// shared/src/commonMain/kotlin/com/connectyourcoach/connectyourcoach/App.kt
package com.connectyourcoach.connectyourcoach

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val cameraService = remember { createCameraGalleryService() }
            CameraGalleryScreen(cameraService)
        }
    }
}

@Composable
fun CameraGalleryScreen(cameraService: CameraGalleryService) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Aquest botó obrirà directament la càmera
        cameraService.OpenCamera { bytes ->
            // Processar els bytes de la imatge aquí
            Text("Imatge de càmera rebuda: ${bytes?.size ?: 0} bytes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Aquest botó obrirà directament la galeria
        cameraService.OpenGallery { bytes ->
            // Processar els bytes de la imatge aquí
            Text("Imatge de galeria rebuda: ${bytes?.size ?: 0} bytes")
        }
    }
}