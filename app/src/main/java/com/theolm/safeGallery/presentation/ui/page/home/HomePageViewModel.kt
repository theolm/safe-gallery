package com.theolm.safeGallery.presentation.ui.page.home

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor() : ViewModel() {
    private val bioCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            lock()
        }

        override fun onAuthenticationSucceeded(
            result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            unlock()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            lock()
        }
    }

    var isUnLocked by mutableStateOf(false)
        private set

    fun unlock() {
        isUnLocked = true
    }

    fun lock() {
        isUnLocked = false
    }

    fun askBiometric(context: Context) {
        val act = context as FragmentActivity
        val executor = ContextCompat.getMainExecutor(context)

        val biometricPrompt = BiometricPrompt(act, executor, bioCallback)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}