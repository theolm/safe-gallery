package com.theolm.safeGallery.presentation.ui.page.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.biometric.AuthResponse
import com.theolm.biometric.useCase.AuthenticateUseCase
import com.theolm.core.data.AppAuthState
import com.theolm.core.data.LockState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val appAuthState: AppAuthState,
    private val biometricAuth : AuthenticateUseCase
) : ViewModel() {
    val lockState : Flow<LockState> = appAuthState.lockState

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