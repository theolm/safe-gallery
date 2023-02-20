package com.theolm.core.usecase

import com.theolm.core.data.SafeNote
import com.theolm.core.repository.SafeNotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetSafeNotesUseCase {
    operator fun invoke(): Flow<List<SafeNote>>
}

internal class GetSafeNotesUseCaseImpl @Inject constructor(
    private val repository: SafeNotesRepository
) : GetSafeNotesUseCase{
    override fun invoke(): Flow<List<SafeNote>> = repository.getSafeNotesFlow()
}
