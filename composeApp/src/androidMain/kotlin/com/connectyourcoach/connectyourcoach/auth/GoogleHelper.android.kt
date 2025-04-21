package com.connectyourcoach.connectyourcoach.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

actual class GoogleAuthHelper actual constructor() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private var onSuccess: ((String) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null
    private var activity: Activity? = null

    fun setActivity(activity: Activity) {
        this.activity = activity
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("YOUR_ANDROID_CLIENT_ID")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    actual fun signIn() {
        val currentActivity = activity ?: throw IllegalStateException("Activity not set")
        val signInIntent = googleSignInClient.signInIntent
        currentActivity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    actual fun setCallbacks(
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onError = onError
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { token ->
                    onSuccess?.invoke(token)
                } ?: run {
                    onError?.invoke(Exception("No token received"))
                }
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}