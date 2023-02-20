package com.theolm.safeGallery.presentation.ui.page.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.theolm.core.usecase.CreateTempFileUseCase
import com.theolm.core.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val createTempFileUseCase: CreateTempFileUseCase,
    getPhotosUseCase: GetPhotosUseCase,
) : ViewModel() {
    val photos = getPhotosUseCase()

    var tempUri: Uri? = null
        private set

    fun refreshTempFile() {
        tempUri = createTempFileUseCase()
    }
}