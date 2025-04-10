package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import io.github.kashif_mehmood_km.camerak.permissions.rememberPermissionsManager

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    val permissionsManager = rememberPermissionsManager(
        onPermissionsResult = { granted ->
            if (granted) {
                callback.onPermissionGranted()
            } else {
                callback.onPermissionDenied()
            }
        }
    )
    return object : PermissionsManager(callback) {
        override fun requestPermissions() {
            permissionsManager.requestPermissions()
        }
    }
}

actual abstract class PermissionsManager actual constructor(protected val callback: PermissionCallback) : PermissionHandler {
    actual abstract fun requestPermissions()
}
