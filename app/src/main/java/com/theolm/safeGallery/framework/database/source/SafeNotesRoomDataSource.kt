package com.theolm.safeGallery.framework.database.source

import com.theolm.core.data.SafeNote
import com.theolm.core.repository.NotesDataSource
import com.theolm.safeGallery.framework.database.dao.SafeNotesDAO
import com.theolm.safeGallery.framework.database.entities.SafeNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SafeNotesRoomDataSource @Inject constructor(
    private val notesDAO: SafeNotesDAO
) : NotesDataSource {
    override suspend fun saveNote(note: SafeNote) {
        notesDAO.insertNote(SafeNoteEntity.fromSafeNote(note))
    }

    override suspend fun deleteNote(note: SafeNote) {
        notesDAO.deleteNote(SafeNoteEntity.fromSafeNote(note))
    }

    override fun getSafeNotesFlow(): Flow<List<SafeNote>> = notesDAO.getAll().map { list ->
        list.map { it.toSafeNote() }
    }
}