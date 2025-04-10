package com.connectyourcoach.connectyourcoach.views

import androidx.compose.runtime.Composable
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationController
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import kotlin.coroutines.suspendCoroutine

actual class MediaPicker actual constructor() {
    private var currentViewController: UIViewController? = null

    fun setViewController(viewController: UIViewController) {
        this.currentViewController = viewController
    }

    @Composable
    actual fun OpenCamera(onImageSelected: (String) -> Unit) {
        showImagePicker(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera, onImageSelected)
    }

    @Composable
    actual fun OpenGallery(onImageSelected: (String) -> Unit) {
        showImagePicker(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary, onImageSelected)
    }

    private fun showImagePicker(sourceType: UIImagePickerControllerSourceType, onImageSelected: (String) -> Unit) {
        val picker = UIImagePickerController().apply {
            setSourceType(sourceType)
            setAllowsEditing(true)
            setDelegate(object : NSObject(), UIImagePickerControllerDelegateProtocol {
                override fun imagePickerController(
                    picker: UIImagePickerController,
                    didFinishPickingMediaWithInfo: Map<Any?, *>
                ) {
                    val imageUrl = "ios_captured_image_${System.currentTimeMillis()}" // Simulació
                    onImageSelected(imageUrl)
                    picker.dismissViewControllerAnimated(true, null)
                }

                override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                    picker.dismissViewControllerAnimated(true, null)
                }
            })
        }

        currentViewController?.let { vc ->
            vc.presentViewController(picker, animated = true, completion = null)
        }
    }
}