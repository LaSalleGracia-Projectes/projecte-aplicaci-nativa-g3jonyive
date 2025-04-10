package com.connectyourcoach.connectyourcoach.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.File

actual class MediaPicker actual constructor() {
    @Composable
    actual fun OpenCamera(onImageSelected: (String) -> Unit) {
        val context = LocalContext.current
        // Utilitzem mutableStateOf per a gestionar l'estat de manera reactiva
        val currentPhotoUri = remember { mutableStateOf<Uri?>(null) }

        val takePhotoLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                // Obtenim el valor actual de manera segura
                currentPhotoUri.value?.let { uri ->
                    onImageSelected(uri.toString())
                }
            }
        }

        // Crear fitxer temporal
        val photoFile = File.createTempFile(
            "photo_",
            ".jpg",
            context.externalCacheDir
        ).apply {
            createNewFile()
        }

        // Assignem el valor a l'estat
        currentPhotoUri.value = Uri.fromFile(photoFile)

        // Llançament del launcher amb el valor actual
        currentPhotoUri.value?.let { uri ->
            takePhotoLauncher.launch(uri)
        }
    }

    @Composable
    actual fun OpenGallery(onImageSelected: (String) -> Unit) {
        val pickPhotoLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                onImageSelected(it.toString())
            }
        }

        pickPhotoLauncher.launch("image/*")
    }
}