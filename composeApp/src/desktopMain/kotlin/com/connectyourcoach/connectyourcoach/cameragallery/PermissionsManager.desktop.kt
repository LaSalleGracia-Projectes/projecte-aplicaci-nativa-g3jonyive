import androidx.compose.runtime.Composable

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    TODO("Not yet implemented")
}

actual class PermissionsManager actual constructor(callback: PermissionCallback) : PermissionHandler