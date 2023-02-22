package com.theolm.safeGallery.framework.database.entities

import com.theolm.core.data.SafeNote
import org.junit.Assert
import org.junit.Test


internal class SafeNoteEntityTest {

    @Test
    fun `given a SafeNote X, expects fromSafeNote(X) to return a SafeNoteEntity with the correct values`() {
        val safeNote = SafeNote(
            uid = 1,
            note = "Test Note",
            createdAt = 111,
            updatedAt = 222
        )

        val entity = SafeNoteEntity.fromSafeNote(safeNote)

        Assert.assertEquals(safeNote.uid, entity.uid)
        Assert.assertEquals(safeNote.note, entity.note)
        Assert.assertEquals(safeNote.createdAt, entity.createdAt)
        Assert.assertEquals(safeNote.updatedAt, entity.updatedAt)
    }

    @Test
    fun `given a SafeNoteEntity X, expects X_toSafeNote to return a SafeNote with the correct values`() {
        val entity = SafeNoteEntity(
            uid = 1,
            note = "Test Note",
            createdAt = 111,
            updatedAt = 222
        )

        val safeNote = entity.toSafeNote()

        Assert.assertEquals(entity.uid, safeNote.uid)
        Assert.assertEquals(entity.note, safeNote.note)
        Assert.assertEquals(entity.createdAt, safeNote.createdAt)
        Assert.assertEquals(entity.updatedAt, safeNote.updatedAt)
    }
}