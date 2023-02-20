package com.theolm.core.usecase

import com.theolm.core.data.SafePhoto
import com.theolm.core.repository.SafeGalleryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetPhotosUseCase {
    operator fun invoke(): Flow<List<SafePhoto>>
}

internal class GetPhotosUseCaseImpl @Inject constructor(
    private val repository: SafeGalleryRepository
) : GetPhotosUseCase {
    override fun invoke(): Flow<List<SafePhoto>> = repository.getPhotos()
}