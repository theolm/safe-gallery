package com.theolm.core.repository

import com.theolm.core.data.SafeNote
import kotlinx.coroutines.flow.Flow

interface NotesDataSource {
    suspend fun saveNote(note : SafeNote)

    suspend fun deleteNote(note: SafeNote)

    fun getSafeNotesFlow() : Flow<List<SafeNote>>
}