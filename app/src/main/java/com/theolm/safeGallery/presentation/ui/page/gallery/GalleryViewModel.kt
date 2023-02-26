package com.theolm.safeGallery.presentation.ui.page.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.theolm.core.data.AppAuthState
import com.theolm.core.usecase.CreateTempFileUseCase
import com.theolm.core.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val appAuthState: AppAuthState,
    private val createTempFileUseCase: CreateTempFileUseCase,
    getPhotosUseCase: GetPhotosUseCase,
) : ViewModel() {
    val photos = getPhotosUseCase().map {
        it.sortedByDescending { it.lastModified }
    }

    var tempUri: Uri? = null
        private set

    fun refreshTempFile() {
        tempUri = createTempFileUseCase()
    }

    suspend fun bypassLock() {
        appAuthState.bypassLockOnce()
    }
}