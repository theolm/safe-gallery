package com.theolm.safeGallery.presentation.ui.page.preview

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.theolm.core.data.SafePhoto
import com.theolm.core.usecase.SavePhotoUseCase
import com.theolm.safeGallery.presentation.ui.page.destinations.PreviewPageDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewPageViewModel @Inject constructor(
    private val savePhotoUseCase: SavePhotoUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(getInitialState())
        private set


    fun onImageClicked() {
        uiState = uiState.copy(
            visibleTools = !uiState.visibleTools
        )
    }

    //TODO: replace return for states and observables
    suspend fun savePhoto() = savePhotoUseCase.invoke(SafePhoto(uiState.uri))

    private fun getInitialState() =
        when (val type = PreviewPageDestination.argsFrom(savedStateHandle).pageType) {
            is PreviewPageType.NewPhoto -> PreviewPageUiState(
                uri = type.tempImageUri,
                type = type,
            )
            is PreviewPageType.Photo -> PreviewPageUiState(
                uri = type.photo.uri,
                type = type,
            )
        }
}

data class PreviewPageUiState(
    val uri: Uri,
    val type: PreviewPageType,
    val visibleTools: Boolean = true,
)
