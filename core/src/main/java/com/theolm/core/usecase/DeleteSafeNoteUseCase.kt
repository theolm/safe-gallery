package com.theolm.core.usecase

import com.theolm.core.data.SafeNote
import com.theolm.core.repository.SafeNotesRepository
import javax.inject.Inject

interface DeleteSafeNoteUseCase {
    suspend fun delete(note: SafeNote)
}

internal class DeleteSafeNoteUseCaseImpl @Inject constructor(
    private val repository: SafeNotesRepository
) : DeleteSafeNoteUseCase{
    override suspend fun delete(note: SafeNote) {
        repository.deleteNote(note)
    }

}
