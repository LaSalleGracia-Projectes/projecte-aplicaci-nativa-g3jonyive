// shared/src/androidMain/kotlin/com/connectyourcoach/connectyourcoach/AndroidCameraGalleryService.kt
package com.connectyourcoach.connectyourcoach

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.ByteArrayOutputStream
import java.io.File

actual fun createCameraGalleryService(): CameraGalleryService = object : CameraGalleryService {

    @Composable
    override fun OpenCamera(onResult: (ByteArray?) -> Unit) {
        val context = LocalContext.current
        val photoFile = remember {
            File.createTempFile(
                "photo_${System.currentTimeMillis()}",
                ".jpg",
                context.cacheDir
            )
        }
        val photoUri = remember { Uri.fromFile(photoFile) }
        var launchCamera by remember { mutableStateOf(false) }

        val cameraLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                try {
                    val bytes = photoFile.readBytes()
                    onResult(bytes)
                    photoFile.delete()
                } catch (e: Exception) {
                    onResult(null)
                }
            } else {
                onResult(null)
            }
        }

        if (launchCamera) {
            LaunchedEffect(Unit) {
                cameraLauncher.launch(photoUri)
                launchCamera = false
            }
        }

        // Això permetrà que el botó funcioni correctament
        LaunchedEffect(Unit) {
            launchCamera = true
        }
    }

    @Composable
    override fun OpenGallery(onResult: (ByteArray?) -> Unit) {
        var launchGallery by remember { mutableStateOf(false) }

        val galleryLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { imageUri ->
                val context = LocalContext.current
                try {
                    context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                        val outputStream = ByteArrayOutputStream()
                        inputStream.copyTo(outputStream)
                        onResult(outputStream.toByteArray())
                    } ?: onResult(null)
                } catch (e: Exception) {
                    onResult(null)
                }
            } ?: onResult(null)
        }

        if (launchGallery) {
            LaunchedEffect(Unit) {
                galleryLauncher.launch("image/*")
                launchGallery = false
            }
        }

        // Això permetrà que el botó funcioni correctament
        LaunchedEffect(Unit) {
            launchGallery = true
        }
    }
}