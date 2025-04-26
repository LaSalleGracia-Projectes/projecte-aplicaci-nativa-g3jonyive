package com.connectyourcoach.connectyourcoach.auth

import android.app.Activity
import android.content.Context
import com.connectyourcoach.connectyourcoach.MainActivity

actual fun launchGoogleSignIn(
    onResult: (idToken: String?) -> Unit
) {
    // Aquesta implementació necessita un Context/Activity
    // Es recomana passar-ho com a paràmetre o usar un patró DI
    throw NotImplementedError("S'ha de proporcionar un Context o Activity")
}

// Versió alternativa que accepta Context
fun launchGoogleSignInWithContext(
    context: Context,
    onResult: (idToken: String?) -> Unit
) {
    if (context is Activity) {
        val helper = MainActivity.googleAuthHelper
        helper.setActivity(context)
        helper.setCallbacks(
            onSuccess = { idToken -> onResult(idToken) },
            onError = { onResult(null) }
        )
        helper.signIn()
    } else {
        onResult(null)
    }
}