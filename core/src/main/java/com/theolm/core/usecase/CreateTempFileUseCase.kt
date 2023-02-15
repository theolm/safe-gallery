package com.theolm.core.usecase

import android.net.Uri
import com.theolm.core.repository.SafeGalleryRepository
import javax.inject.Inject

interface CreateTempFileUseCase {
    operator fun invoke() : Uri
}

internal class CreateTempFileUseCaseImpl @Inject constructor(
    private val repository: SafeGalleryRepository
) : CreateTempFileUseCase{
    override fun invoke() = repository.createTempFile()
}
