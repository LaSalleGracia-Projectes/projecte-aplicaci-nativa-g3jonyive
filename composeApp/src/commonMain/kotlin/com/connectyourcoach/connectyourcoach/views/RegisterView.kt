package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.connectyourcoach.connectyourcoach.viewmodels.RegisterViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
expect fun OpenCamera(onImageSelected: (String) -> Unit)

@Composable
expect fun OpenGallery(onImageSelected: (String) -> Unit)

@Composable
fun RegisterPhotoUsernameView(
    viewModel: RegisterViewModel,
    onRegisterComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Register", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))

        // Avatar Section
        Box(contentAlignment = Alignment.BottomEnd) {
            KamelImage(
                resource = asyncPainterResource(data = viewModel.avatarUrl.ifEmpty { "https://example.com/default_avatar.png" }),
                contentDescription = "User avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colors.primary, CircleShape),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { viewModel.showAvatarOptions = true },
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colors.primary, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Change avatar",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar Options Dialog
        if (viewModel.showAvatarOptions) {
            AlertDialog(
                onDismissRequest = { viewModel.showAvatarOptions = false },
                title = { Text("Select Avatar") },
                text = {
                    Column {
                        AvatarOptionButton(
                            icon = Icons.Default.ThumbUp,
                            text = "Take photo",
                            onClick = {
                                viewModel.showAvatarOptions = false
                                OpenCamera { url ->
                                    viewModel.avatarUrl = url
                                }
                            }
                        )
                        AvatarOptionButton(
                            icon = Icons.Default.Face,
                            text = "Choose from gallery",
                            onClick = {
                                viewModel.showAvatarOptions = false
                                OpenGallery { url ->
                                    viewModel.avatarUrl = url
                                }
                            }
                        )
                        AvatarOptionButton(
                            icon = Icons.Default.Refresh,
                            text = "Generate random avatar",
                            onClick = {
                                viewModel.showAvatarOptions = false
                                viewModel.generateRandomAvatar()
                            }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { viewModel.showAvatarOptions = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Form Fields
        TextField(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (viewModel.registerError.isNotEmpty()) {
            Text(
                text = viewModel.registerError,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (viewModel.isValidRegister()) {
                    viewModel.onRegister(onRegisterComplete)
                } else {
                    viewModel.registerError = "Please fill all fields correctly and select an avatar"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = viewModel.isValidRegister() && !viewModel.isLoading
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Register")
            }
        }
    }
}