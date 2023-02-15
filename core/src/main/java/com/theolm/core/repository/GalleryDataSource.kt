package com.theolm.core.repository

import android.net.Uri
import com.theolm.core.data.SafePhoto

interface GalleryDataSource {
    fun createTempFile() : Uri
    fun savePhoto(photo: SafePhoto) : Boolean
}
