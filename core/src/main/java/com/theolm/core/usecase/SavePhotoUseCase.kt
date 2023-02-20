package com.theolm.core.usecase

import com.theolm.core.data.SafePhoto
import com.theolm.core.repository.SafeGalleryRepository
import javax.inject.Inject

interface SavePhotoUseCase {
    suspend operator fun invoke(photo: SafePhoto): Boolean
}

internal class SavePhotoUseCaseImpl @Inject constructor(
    private val repository: SafeGalleryRepository
) : SavePhotoUseCase {
    override suspend fun invoke(photo: SafePhoto) = repository.savePhoto(photo)
}