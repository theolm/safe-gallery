package com.theolm.core.usecase

import com.theolm.core.data.SafeNote
import com.theolm.core.repository.SafeNotesRepository
import javax.inject.Inject

interface DeleteSafeNoteUseCase {
    suspend operator fun invoke(note: SafeNote)
}

internal class DeleteSafeNoteUseCaseImpl @Inject constructor(
    private val repository: SafeNotesRepository
) : DeleteSafeNoteUseCase{
    override suspend fun invoke(note: SafeNote) {
        repository.deleteNote(note)
    }

}
