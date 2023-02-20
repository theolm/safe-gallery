package com.theolm.safeGallery.framework.gallery

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.theolm.core.data.SafePhoto
import com.theolm.core.repository.GalleryDataSource
import com.theolm.safeGallery.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject


class SafeGalleryDataSource @Inject constructor(private val application: Application) :
    GalleryDataSource {
    private val sharedFlow =
        MutableSharedFlow<List<SafePhoto>>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    private val imagesFolder = getImagesFolder()

    init {
        updatePhotoFlow()
    }

    override fun createTempFile(): Uri {
        val tmpFile = File.createTempFile(tempFileName, tempFileExt, application.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            application,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    override suspend fun savePhoto(photo: SafePhoto): Boolean {
        application.contentResolver.openInputStream(photo.uri)?.let {
            try {
                withContext(Dispatchers.IO) {
                    val outputFile = File(imagesFolder, getImageFileName())
                    val outputStream = FileOutputStream(outputFile)
                    it.copyTo(outputStream)
                    outputStream.flush()
                    it.close()
                }
                updatePhotoFlow()
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }

        return false
    }

    override suspend fun deletePhoto(photo: SafePhoto): Boolean {
        return try {
            val file = photo.uri.toFile()
            val result = file.delete()
            updatePhotoFlow()
            result
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun getPhotos() = sharedFlow

    private fun getImageFileName(): String {
        val date = Date().toString().replace(" ", "_").replace(":", "-")
        return "photo_$date$tempFileExt"
    }

    private fun updatePhotoFlow() {
        val photos = listPhotos()
        sharedFlow.tryEmit(photos)
    }

    //TODO: Move to utils class
    private fun getImagesFolder() : File {
        val folder = File(application.filesDir, images)
        if (!folder.exists()) folder.mkdir()

        return folder
    }

    private fun listPhotos() : List<SafePhoto> {
        val files = imagesFolder.listFiles() ?: return emptyList()
        return if (files.isEmpty()) {
            emptyList()
        } else {
            files.map {
                SafePhoto(it.toUri())
            }
        }
    }

    private companion object {
        const val tempFileName = "tmp_image_file"
        const val tempFileExt = ".jpg"
        const val images = "images"
    }
}