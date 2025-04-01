package com.connectyourcoach.connectyourcoach

import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraDevice
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal actual fun openIOSCamera(): String? = runOnMainThread {
    val imageUrl = suspendCoroutine<String?> { continuation ->
        val picker = UIImagePickerController().apply {
            sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            cameraDevice = UIImagePickerControllerCameraDevice.UIImagePickerControllerCameraDeviceRear
            delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    // Processar la imatge i obtenir URL
                    continuation.resume("image_ios_${System.currentTimeMillis()}.jpg")
                    picker.dismissViewControllerAnimated(true, null)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    continuation.resume(null)
                    picker.dismissViewControllerAnimated(true, null)
                }
            }
        }
        getRootViewController()?.presentViewController(picker, animated = true, completion = null)
    }
    return imageUrl
}

internal actual fun openIOSGallery(): String? = runOnMainThread {
    val imageUrl = suspendCoroutine<String?> { continuation ->
        val picker = UIImagePickerController().apply {
            sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
                UINavigationControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    // Processar la imatge i obtenir URL
                    continuation.resume("image_ios_${System.currentTimeMillis()}.jpg")
                    picker.dismissViewControllerAnimated(true, null)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    continuation.resume(null)
                    picker.dismissViewControllerAnimated(true, null)
                }
            }
        }
        getRootViewController()?.presentViewController(picker, animated = true, completion = null)
    }
    return imageUrl
}

private interface UIImagePickerControllerDelegateProtocol {
    fun imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>)
    fun imagePickerControllerDidCancel(picker: UIImagePickerController)
}

private fun getRootViewController(): UIViewController? {
    return UIApplication.sharedApplication.keyWindow?.rootViewController
}

private suspend fun <T> runOnMainThread(block: suspend () -> T): T {
    return kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
        block()
    }
}