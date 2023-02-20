package com.theolm.core.repository

import android.net.Uri
import com.theolm.core.data.SafePhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal interface SafeGalleryRepository {
    fun createTempFile(): Uri
    suspend fun savePhoto(photo: SafePhoto): Boolean
    suspend fun deletePhoto(photo: SafePhoto): Boolean

    fun getPhotos(): Flow<List<SafePhoto>>
}

internal class SafeGalleryRepositoryImpl @Inject constructor(
    private val dataSource: GalleryDataSource
) : SafeGalleryRepository {
    override fun createTempFile(): Uri = dataSource.createTempFile()

    override suspend fun savePhoto(photo: SafePhoto): Boolean = dataSource.savePhoto(photo)
    override suspend fun deletePhoto(photo: SafePhoto): Boolean = dataSource.deletePhoto(photo)
    override fun getPhotos(): Flow<List<SafePhoto>> = dataSource.getPhotos()
}
