package com.theolm.core.repository

import android.net.Uri
import com.theolm.core.data.SafePhoto
import javax.inject.Inject

internal interface SafeGalleryRepository {
    fun createTempFile(): Uri
    fun savePhoto(photo: SafePhoto): Boolean
}

internal class SafeGalleryRepositoryImpl @Inject constructor(
    private val dataSource: GalleryDataSource
) : SafeGalleryRepository {
    override fun createTempFile(): Uri = dataSource.createTempFile()

    override fun savePhoto(photo: SafePhoto): Boolean = dataSource.savePhoto(photo)
}
