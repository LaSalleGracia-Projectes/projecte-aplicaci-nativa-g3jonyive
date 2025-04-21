package com.connectyourcoach.connectyourcoach.auth

class AuthRepository(private val googleAuthHelper: GoogleAuthHelper) {
    private var onSuccess: ((String) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null // Make onError nullable

    init {
        googleAuthHelper.setCallbacks(
            onSuccess = { token ->
                onSuccess?.invoke(token)
            },
            onError = { error ->
                onError?.invoke(error) // Invoke onError if it's not null
            }
        )
    }

    fun loginWithGoogle(
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onError = onError
        googleAuthHelper.signIn()
    }
}