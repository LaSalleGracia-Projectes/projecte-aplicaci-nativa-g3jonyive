package com.connectyourcoach.connectyourcoach.cameragallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

actual class SharedImage(private val bitmap: Bitmap) {

    actual fun toByteArray(): ByteArray? {
        return try {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.toByteArray()
        } catch (e: Exception) {
            null
        }
    }

    actual fun toImageBitmap(): ImageBitmap? {
        return try {
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }
}
