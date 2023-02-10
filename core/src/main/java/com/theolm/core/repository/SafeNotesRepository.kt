package com.theolm.core.repository

import com.theolm.core.data.SafeNote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SafeNotesRepository {
    suspend fun saveNote(note: SafeNote)
    fun getSafeNotesFlow(): Flow<List<SafeNote>>
    suspend fun deleteNote(note: SafeNote)
}

internal class SafeNotesRepositoryImpl @Inject constructor(
    private val dataSource: NotesDataSource
) :
    SafeNotesRepository {
    override suspend fun saveNote(note: SafeNote) {
        dataSource.saveNote(note)
    }

    override fun getSafeNotesFlow(): Flow<List<SafeNote>> = dataSource.getSafeNotesFlow()

    override suspend fun deleteNote(note: SafeNote) {
        dataSource.deleteNote(note)
    }
}
