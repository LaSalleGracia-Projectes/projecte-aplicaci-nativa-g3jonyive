package com.connectyourcoach.connectyourcoach.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil3.compose.SubcomposeAsyncImage
import com.connectyourcoach.connectyourcoach.apicamera.ApiCamera
import com.connectyourcoach.connectyourcoach.viewmodels.SettingsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import shared.PermissionCallback
import shared.PermissionStatus
import shared.PermissionType
import shared.createPermissionsManager
import shared.rememberGalleryManager

@Composable
fun SettingsProfileView(
    viewModel: SettingsViewModel,
    paddingValues: PaddingValues,
    onSave: () -> Unit,
) {
    val fullname by viewModel.full_name
    val phone by viewModel.phone
    val photoUrl by viewModel.photoUrl
    val error by viewModel.error
    val imageBitmap by viewModel.imageBitmap
    val launchGallery by viewModel.launchGallery

    val galleryManager = rememberGalleryManager { image ->
        viewModel.onImageSelected(image)
    }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {

        }
    })

    if (viewModel.saved.value) {
        onSave()
    }

    if (viewModel.loading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        return
    }

    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        viewModel.closeGallery()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings de Perfil", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(50.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (imageBitmap == null) {
                SubcomposeAsyncImage(
                    model = photoUrl,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(100.dp),
                    loading = {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary
                        )
                    },
                    error = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            modifier = Modifier.size(100.dp),
                            contentDescription = "Error on loading image",
                        )
                    }
                )
            } else {
                Image(
                    modifier = Modifier.size(100.dp),
                    bitmap = imageBitmap!!,
                    contentDescription = "Profile",
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { viewModel.onGenerateNewAvatar() },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Generate Avatar")
                }

                Spacer(modifier = Modifier.weight(0.5f))

                Button(
                    onClick = { viewModel.openGallery() },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Upload Image")
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Edit profile",
            style = MaterialTheme.typography.h5,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fullname,
            onValueChange = { viewModel.onFullNameChange(it) },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.onPhoneChange(it) },
            label = { Text("Phone (optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        if (!viewModel.isValidPhone() && phone.isNotEmpty()) {
            Text(
                text = "Please enter a valid phone number",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.isValidSave(),
            onClick = { viewModel.onSave() }
        ) {
            if (viewModel.loading.value) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Save Changes")
            }
        }

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}