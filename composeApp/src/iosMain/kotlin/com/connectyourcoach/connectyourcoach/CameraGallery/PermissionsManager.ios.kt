package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    TODO("Not yet implemented")
}

actual abstract class PermissionsManager actual constructor(callback: PermissionCallback) :
    PermissionHandler