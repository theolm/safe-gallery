package com.theolm.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal interface BiometricAuth {
    suspend fun authenticate(activity: FragmentActivity): AuthResponse
}

internal class BiometricAuthImpl @Inject constructor() : BiometricAuth {
    override suspend fun authenticate(activity: FragmentActivity): AuthResponse =
        suspendCancellableCoroutine { continuation ->

            val executor = ContextCompat.getMainExecutor(activity)

            val bioCallback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (continuation.isActive) {
                        continuation.resume(AuthResponse.AuthenticationError(errorCode, errString))
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    if (continuation.isActive) {
                        continuation.resume(AuthResponse.AuthenticationSucceeded)
                    }
                }
            }

            val biometricPrompt = BiometricPrompt(activity, executor, bioCallback)

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(activity.getString(R.string.prompt_title))
                .setSubtitle(activity.getString(R.string.prompt_message))
                .setNegativeButtonText(activity.getString(R.string.prompt_negative))
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
}
