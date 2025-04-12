package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.*
import platform.CoreGraphics.CGImageRef
import platform.Foundation.*
import platform.UIKit.*

actual class SharedImage(private val uiImage: UIImage) {

    actual fun toByteArray(): ByteArray? {
        // Intentem obtenir el PNG de la imatge amb UIImagePNGRepresentation
        val data = UIImagePNGRepresentation(uiImage)
        return data?.toByteArray()
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun toImageBitmap(): ImageBitmap? {
        val cgImage: CGImageRef? = uiImage.CGImage
        return cgImage?.let {
            org.jetbrains.skia.Image.makeFromEncoded(toByteArray()!!).toComposeImageBitmap()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun NSData.toByteArray(): ByteArray {
        val byteArray = ByteArray(this.length.toInt())
        memScoped {
            val buffer = allocArray<ByteVar>(this@toByteArray.length.toInt())
            this@toByteArray.getBytes(buffer, this@toByteArray.length)
            for (i in byteArray.indices) {
                byteArray[i] = buffer[i]
            }
        }
        return byteArray
    }
}
