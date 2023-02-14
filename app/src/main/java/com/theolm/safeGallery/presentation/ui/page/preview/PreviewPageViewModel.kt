package com.theolm.safeGallery.presentation.ui.page.preview

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewPageViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(PreviewPageUiState())
        private set

    fun loadBitmap(bitmap: Bitmap) {
        uiState = uiState.copy(image = bitmap)
    }
}

data class PreviewPageUiState(
    val image: Bitmap? = null
)