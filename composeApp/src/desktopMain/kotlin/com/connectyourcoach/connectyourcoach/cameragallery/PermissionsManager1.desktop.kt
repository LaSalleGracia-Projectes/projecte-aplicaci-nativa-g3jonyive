import androidx.compose.runtime.Composable

actual class PermissionsManager actual constructor(callback: PermissionCallback) : PermissionHandler

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    TODO("Not yet implemented")
}