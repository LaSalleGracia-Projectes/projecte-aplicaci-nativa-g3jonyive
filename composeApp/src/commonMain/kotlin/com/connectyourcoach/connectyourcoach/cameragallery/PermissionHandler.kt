package com.connectyourcoach.connectyourcoach.cameragallery

import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.cameragallery.PermissionType

interface PermissionHandler {
    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()

}