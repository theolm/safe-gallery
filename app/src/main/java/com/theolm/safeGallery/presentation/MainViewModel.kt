package com.theolm.safeGallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.core.data.AppAuthState
import com.theolm.core.data.LockState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appAuthState: AppAuthState) : ViewModel() {
    val lockState : Flow<LockState> = appAuthState.lockState

    fun lockApp() {
        viewModelScope.launch {
            appAuthState.lock()
        }
    }
}