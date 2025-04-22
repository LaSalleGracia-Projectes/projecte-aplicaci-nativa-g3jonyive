package com.connectyourcoach.connectyourcoach.apicamera

sealed class ImageState {
    object Loading : ImageState()
    data class Error(val message: String) : ImageState()
    data class Success(val imageUrl: String) : ImageState()
}