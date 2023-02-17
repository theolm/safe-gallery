package com.theolm.safeGallery.presentation.ui.page.preview

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.theolm.safeGallery.presentation.ui.page.destinations.PreviewPageDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewPageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(PreviewPageUiState(getTempUri()))
        private set


    fun savePhoto() {

    }

    private fun getTempUri() =
        PreviewPageDestination.argsFrom(savedStateHandle).tempImageUri
}

data class PreviewPageUiState(val tempUri: Uri)
