package com.theolm.safeGallery.framework.database.source

import com.theolm.safeGallery.framework.database.dao.SafeNotesDAO
import com.theolm.safeGallery.framework.database.entities.SafeNoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSafeNotesDAO : SafeNotesDAO {
    val fakeDb = arrayListOf<SafeNoteEntity>()
    private val flow = MutableStateFlow(fakeDb.toList())


    override fun getAll(): Flow<List<SafeNoteEntity>> = flow

    override suspend fun insertNote(note: SafeNoteEntity) {
        fakeDb.add(note)
        flow.emit(fakeDb)
    }

    override suspend fun deleteNote(note: SafeNoteEntity) {
        fakeDb.removeIf { note.uid == it.uid }
        flow.emit(fakeDb)
    }

}