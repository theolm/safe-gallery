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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val biometricAuth : AuthenticateUseCase
) : ViewModel() {
    var isUnLocked by mutableStateOf(false)
        private set



    fun lock() {
        isUnLocked = false
    }

    private fun unlock() {
        isUnLocked = true
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