package com.theolm.safeGallery.presentation.ui.page.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.theolm.core.usecase.CreateTempFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    createTempFileUseCase: CreateTempFileUseCase,
) : ViewModel() {
    val tempUri: Uri = createTempFileUseCase()
}