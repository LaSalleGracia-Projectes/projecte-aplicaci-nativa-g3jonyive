package com.connectyourcoach.connectyourcoach.auth



import android.app.Activity
import android.content.Intent

expect object GoogleHelper {
    fun launchGoogleSignIn(activity: Activity, serverClientId: String)
    fun handleGoogleSignInResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?, onSuccess: (String) -> Unit, onError: (String) -> Unit)
}
