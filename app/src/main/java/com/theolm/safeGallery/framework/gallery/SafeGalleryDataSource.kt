package com.theolm.safeGallery.framework.gallery

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import com.theolm.core.data.SafePhoto
import com.theolm.core.repository.GalleryDataSource
import com.theolm.safeGallery.BuildConfig
import java.io.File
import javax.inject.Inject

class SafeGalleryDataSource @Inject constructor(private val application: Application) :
    GalleryDataSource {
    override fun createTempFile(): Uri {
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

    override fun savePhoto(photo: SafePhoto): Boolean {
        //TODO: implement
        return true
    }
}