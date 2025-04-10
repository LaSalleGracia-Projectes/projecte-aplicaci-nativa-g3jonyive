package shared

import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.cameragallery.PermissionType

expect class PermissionsManager(callback: PermissionCallback) : PermissionHandler

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)
}

@Composable
expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager