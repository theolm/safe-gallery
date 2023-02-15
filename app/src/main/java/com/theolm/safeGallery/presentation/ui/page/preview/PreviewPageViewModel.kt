package com.theolm.safeGallery.presentation.ui.page.preview

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.theolm.safeGallery.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PreviewPageViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {
    var uiState by mutableStateOf(PreviewPageUiState(getTmpFileUri()))
        private set

    fun loadImage(uri: Uri) {
        uiState = uiState.copy(uri = uri)
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".jpg", application.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            application,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }
}

data class PreviewPageUiState(
    val tempUri: Uri,
    val uri: Uri? = null,
)
