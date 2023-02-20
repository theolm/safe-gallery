package com.theolm.core.repository

import android.net.Uri
import com.theolm.core.data.SafePhoto
import kotlinx.coroutines.flow.Flow

interface GalleryDataSource {
    fun createTempFile() : Uri
    suspend fun savePhoto(photo: SafePhoto) : Boolean
    suspend fun deletePhoto(photo: SafePhoto) : Boolean
    fun getPhotos() : Flow<List<SafePhoto>>

}
