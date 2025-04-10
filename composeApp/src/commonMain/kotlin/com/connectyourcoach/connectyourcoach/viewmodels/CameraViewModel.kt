import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.connectyourcoach.connectyourcoach.views.MediaPicker

class CameraViewModel(private val mediaPicker: MediaPicker) : ViewModel() {
    var imageUrl by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    @Composable
    fun openCamera() {
        mediaPicker.OpenCamera { url ->
            imageUrl = url
            // Aquí podries afegir lògica per pujar la imatge a un servidor
        }
    }

    @Composable
    fun openGallery() {
        mediaPicker.OpenGallery { url ->
            imageUrl = url
            // Aquí podries afegir lògica per processar la imatge seleccionada
        }
    }

    fun clearImage() {
        imageUrl = null
        errorMessage = null
    }
}