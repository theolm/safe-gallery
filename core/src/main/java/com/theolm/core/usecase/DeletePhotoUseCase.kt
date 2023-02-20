package com.theolm.core.usecase

import com.theolm.core.data.SafePhoto
import com.theolm.core.repository.SafeGalleryRepository
import javax.inject.Inject

interface DeletePhotoUseCase {
    suspend operator fun invoke(photo: SafePhoto): Boolean
}

internal class DeletePhotoUseCaseImpl @Inject constructor(
    private val repository: SafeGalleryRepository
) : DeletePhotoUseCase {
    override suspend fun invoke(photo: SafePhoto): Boolean =
        repository.deletePhoto(photo)

}