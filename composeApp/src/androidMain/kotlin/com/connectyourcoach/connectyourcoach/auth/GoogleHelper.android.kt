package com.connectyourcoach.connectyourcoach.auth

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

actual class GoogleAuthHelper actual constructor() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private var onResultCallback: ((String?) -> Unit)? = null

    @Composable
    actual fun LaunchSignIn(onResult: (idToken: String?) -> Unit) {
        val context = LocalContext.current
        onResultCallback = onResult

        if (context is Activity) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("TU_CLIENT_ID_FIREBASE") // Reemplazar con tu client ID de Firebase
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(context, gso)

            Button(
                onClick = {
                    val signInIntent = googleSignInClient.signInIntent
                    context.startActivityForResult(signInIntent, RC_SIGN_IN)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión con Google")
            }
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                onResultCallback?.invoke(account.idToken)
            } catch (e: ApiException) {
                onResultCallback?.invoke(null)
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    actual fun launchSignIn(onResult: (idToken: String?) -> Unit) {
    }
}