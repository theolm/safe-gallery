package com.theolm.safeGallery.presentation.ui.page.lock

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.biometric.AuthResponse
import com.theolm.biometric.useCase.AuthenticateUseCase
import com.theolm.core.data.AppAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockPageViewModel @Inject constructor(
    private val appAuthState: AppAuthState,
    private val biometricAuth : AuthenticateUseCase
): ViewModel() {
    private fun unlock() {
        viewModelScope.launch {
            appAuthState.unlock()
        }
    }

    fun askBiometric(context: Context) {
        val act = context as FragmentActivity

        viewModelScope.launch {
            val authenticated = biometricAuth.authenticate(act)
            if (authenticated is AuthResponse.AuthenticationSucceeded) {
                unlock()
            }
        }
    }
}