package com.connectyourcoach.connectyourcoach.cameragallery

import PermissionCallback
import androidx.compose.runtime.Composable
import com.connectyourcoach.connectyourcoach.cameragallery.PermissionType
import shared.PermissionStatus

// Implementació de PermissionsManager per a Desktop
class PermissionsManager(callback: PermissionCallback) : PermissionHandler {
    private val callback = callback

    // Mètode per verificar els permisos en Desktop (aquí es fa una implementació per a Desktop)
    @Composable
    override fun askPermission(permission: PermissionType) {
        // No es fa cap acció per demanar permisos, ja que no calen permisos específics a Desktop
        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
    }

    // Mètode per comprovar si els permisos estan atorgats
    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        // Per Desktop, considerem que els permisos estan atorgats per defecte
        return true
    }

    // Mètode per llançar la configuració dels permisos
    @Composable
    override fun launchSettings() {
        // En Desktop, no hi ha una configuració de permisos que l'usuari pugui obrir des de l'aplicació.
        // Aquest mètode es podria utilitzar per obrir configuracions de l'aplicació si fos necessari.
    }
}

// Creació del PermissionsManager per Desktop
@Composable
fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return PermissionsManager(callback)
}
