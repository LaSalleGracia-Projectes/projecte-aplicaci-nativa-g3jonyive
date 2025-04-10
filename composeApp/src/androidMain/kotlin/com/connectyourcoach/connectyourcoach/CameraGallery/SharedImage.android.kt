package shared

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.jetbrains.skia.Image

actual class SharedImage(private val byteArray: ByteArray) {
    actual fun toByteArray(): ByteArray? {
        return byteArray
    }

    actual fun toImageBitmap(): ImageBitmap? {
        return Image.makeFromEncoded(byteArray).asImageBitmap()
    }
}
