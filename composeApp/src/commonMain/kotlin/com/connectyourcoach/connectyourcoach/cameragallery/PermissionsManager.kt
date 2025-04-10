package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import shared.PermissionStatus

expect abstract class PermissionsManager(callback: PermissionCallback) : PermissionHandler

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)
}

@Composable
expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager