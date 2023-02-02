package com.theolm.biometric

sealed class AuthResponse {
    object AuthenticationSucceeded : AuthResponse()
    data class AuthenticationError(val errorCode: Int, val errString: CharSequence) : AuthResponse()
    object AuthenticationFailed : AuthResponse()
}
