package com.connectyourcoach.connectyourcoach.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
actual fun OpenCamera(onImageSelected: (String) -> Unit) {
    val context = LocalContext.current
    val takePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // Here you would upload the photo and get the URL
            // For now we'll just use a placeholder
            onImageSelected("https://example.com/uploaded_photo.jpg")
        }
    }

    // Create temp file
    val photoFile = File.createTempFile(
        "avatar_",
        ".jpg",
        context.externalCacheDir
    )
    val photoUri = Uri.fromFile(photoFile)

    takePhotoLauncher.launch(photoUri)
}

@Composable
actual fun OpenGallery(onImageSelected: (String) -> Unit) {
    val pickPhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Here you would upload the photo and get the URL
            // For now we'll just use the URI
            onImageSelected(it.toString())
        }
    }

    pickPhotoLauncher.launch("image/*")
}