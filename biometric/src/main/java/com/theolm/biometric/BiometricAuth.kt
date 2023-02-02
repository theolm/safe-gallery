package com.theolm.biometric

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal interface BiometricAuth {
    suspend fun authenticate(activity: FragmentActivity): AuthResponse
}

internal class BiometricAuthImpl @Inject constructor() : BiometricAuth {
    override suspend fun authenticate(activity: FragmentActivity): AuthResponse =
        suspendCoroutine { continuation ->

            val executor = ContextCompat.getMainExecutor(activity)

            val bioCallback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    continuation.resume(AuthResponse.AuthenticationError(errorCode, errString))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    continuation.resume(AuthResponse.AuthenticationSucceeded)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    continuation.resume(AuthResponse.AuthenticationFailed)

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
