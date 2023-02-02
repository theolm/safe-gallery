package com.theolm.biometric.useCase

import androidx.fragment.app.FragmentActivity
import com.theolm.biometric.AuthResponse
import com.theolm.biometric.BiometricAuth
import javax.inject.Inject

interface AuthenticateUseCase {
    suspend fun authenticate(activity: FragmentActivity): AuthResponse
}

internal class AuthenticateUseCaseImpl @Inject constructor(private val biometricAuth: BiometricAuth) :
    AuthenticateUseCase {
    override suspend fun authenticate(activity: FragmentActivity): AuthResponse =
        biometricAuth.authenticate(activity)

}