package com.theolm.core.usecase

import com.theolm.core.data.SafeNote
import com.theolm.core.repository.SafeNotesRepository
import javax.inject.Inject

interface SaveSafeNoteUseCase {
    suspend fun save(note: SafeNote)
}

internal class SaveSafeNoteUseCaseImpl @Inject constructor(
    private val repository: SafeNotesRepository
) : SaveSafeNoteUseCase {
    override suspend fun save(note: SafeNote) {
        repository.saveNote(note)
    }
}
