package com.theolm.safeGallery.presentation.ui.page.preview

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.theolm.core.usecase.CreateTempFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewPageViewModel @Inject constructor(
    createTempFileUseCase: CreateTempFileUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(PreviewPageUiState(createTempFileUseCase()))
        private set

    fun loadImage(uri: Uri) {
        uiState = uiState.copy(uri = uri)
    }
}

data class PreviewPageUiState(
    val tempUri: Uri,
    val uri: Uri? = null,
)
