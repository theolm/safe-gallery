package com.theolm.safeGallery.presentation.ui.page.preview

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.core.data.SafePhoto
import com.theolm.core.usecase.DeletePhotoUseCase
import com.theolm.core.usecase.SavePhotoUseCase
import com.theolm.safeGallery.presentation.ui.page.destinations.PreviewPageDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewPageViewModel @Inject constructor(
    private val savePhotoUseCase: SavePhotoUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var closePage by mutableStateOf(false)
        private set

    var uiState by mutableStateOf(getInitialState())
        private set


    fun onImageClicked() {
        uiState = uiState.copy(
            visibleTools = !uiState.visibleTools
        )
    }

    fun onDeleteClick() {
        uiState = uiState.copy(showDeleteAlert = true)
    }

    fun onDeleteConfirm() {
        viewModelScope.launch {
            when (val type = uiState.type) {
                is PreviewPageType.Photo -> {
                    deletePhotoUseCase.invoke(type.photo)
                    onCloseDeleteAlert()
                    closePage = true
                }
                else -> Unit
            }
        }
    }

    fun onCloseDeleteAlert() {
        uiState = uiState.copy(showDeleteAlert = false)
    }


    fun savePhoto() {
        viewModelScope.launch {
            savePhotoUseCase.invoke(SafePhoto(uiState.uri))
            closePage = true
        }
    }

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
    val showDeleteAlert: Boolean = false
)
