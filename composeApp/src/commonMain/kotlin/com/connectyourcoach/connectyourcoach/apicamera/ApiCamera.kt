package com.connectyourcoach.connectyourcoach.apicamera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient

@Composable
fun ApiCamera(httpClient: HttpClient) {
    val coroutineScope = rememberCoroutineScope()
    val avatarState = remember {
        AvatarState(httpClient, coroutineScope)
    }

    MaterialTheme {
        AvatarScreen(
            state = avatarState.imageState,
            onReload = { avatarState.loadNewAvatar() }
        )
    }
}

@Composable
fun AvatarScreen(
    state: ImageState,
    onReload: () -> Unit
) {
    when (state) {
        is ImageState.Loading -> FullScreenLoading()
        is ImageState.Error -> ErrorMessage(state.message, onReload)
        is ImageState.Success -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AvatarImage(state.imageUrl)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = onReload) {
                    Text("Generar nou avatar")
                }
            }
        }
    }
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun AvatarImage(url: String) {
    KamelImage(
        resource = asyncPainterResource(data = url),
        contentDescription = "Avatar aleatori",
        modifier = Modifier.size(200.dp),
        onLoading = {
            Box(Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(Modifier.size(50.dp))
            }
        },
        onFailure = {
            Box(Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                Text("Error en carregar la imatge")
            }
        }
    )
}

@Composable
fun ErrorMessage(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Tornar a intentar")
        }
    }
}
