package com.connectyourcoach.connectyourcoach

import platform.UIKit.UIDevice
import platform.Foundation.NSBundle

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    
    // Afegim suport per Google Sign-In
    fun configureGoogleAuth() {
        // Obtenim el client ID de l'Info.plist
        val clientId = NSBundle.mainBundle.objectForInfoDictionaryKey("GOOGLE_CLIENT_ID") as? String
            ?: throw IllegalStateException("Google Client ID not found in Info.plist")
        
        // Configuració addicional si és necessari
        println("Google Auth configurat per iOS amb Client ID: ${clientId.take(10)}...")
    }
}

actual fun getPlatform(): Platform {
    val platform = IOSPlatform()
    platform.configureGoogleAuth() // Configurem Google Auth en inicialitzar la plataforma
    return platform
}