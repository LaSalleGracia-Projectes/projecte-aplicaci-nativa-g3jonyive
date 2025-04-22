package com.connectyourcoach.connectyourcoach.apicamera

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AvatarState(
    private val httpClient: HttpClient,
    private val coroutineScope: CoroutineScope
) {
    var imageState by mutableStateOf<ImageState>(ImageState.Loading)
        private set

    private val imageLoader = ImageLoader(httpClient)

    init {
        loadNewAvatar()
    }

    fun loadNewAvatar() {
        imageState = ImageState.Loading
        coroutineScope.launch {
            try {
                val url = imageLoader.getRandomAvatar()
                imageState = ImageState.Success(url)
            } catch (e: Exception) {
                imageState = ImageState.Error(
                    e.message ?: "Error desconegut al carregar la imatge"
                )
            }
        }
    }
}