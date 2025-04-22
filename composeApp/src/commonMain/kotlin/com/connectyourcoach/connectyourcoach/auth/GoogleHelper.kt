package com.connectyourcoach.connectyourcoach.auth

expect class GoogleAuthHelper() {
    fun signIn()
    fun setCallbacks(onSuccess: (String) -> Unit, onError: (Throwable) -> Unit)
}