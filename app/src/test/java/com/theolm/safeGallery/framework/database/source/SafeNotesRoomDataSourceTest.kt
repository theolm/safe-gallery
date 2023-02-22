package com.theolm.safeGallery.framework.database.source

import android.annotation.SuppressLint
import com.theolm.core.data.SafeNote
import com.theolm.safeGallery.framework.database.dao.SafeNotesDAO
import com.theolm.safeGallery.framework.database.entities.SafeNoteEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test



@OptIn(ExperimentalCoroutinesApi::class)
internal class SafeNotesRoomDataSourceTest {
    lateinit var fakeDAO: SafeNotesDAO
    lateinit var safeNotesRoomDataSource: SafeNotesRoomDataSource

    val fakeNote = SafeNote(
        uid = 123,
        note = "fake",
        createdAt = 123,
        updatedAt = 123
    )

    @Before
    fun setup() {
        fakeDAO = FakeSafeNotesDAO()
        safeNotesRoomDataSource = SafeNotesRoomDataSource(fakeDAO)
    }


    @Test
    fun `an empty db emits an empty flow`() = runTest {
        Assert.assertTrue((fakeDAO as FakeSafeNotesDAO).fakeDb.isEmpty())
        val list = fakeDAO.getAll().first()
        Assert.assertTrue(list.isEmpty())
    }

    @Test
    @SuppressLint("ConstantConditions")
    fun `after saveNote(X) call, expects X to be stored in the db as a SafeNoteEntity`() = runTest {
        safeNotesRoomDataSource.saveNote(fakeNote)
        val storedNote = (fakeDAO as FakeSafeNotesDAO).fakeDb.first()


        Assert.assertTrue(storedNote is SafeNoteEntity)
        Assert.assertEquals(fakeNote.uid, storedNote.uid)
        Assert.assertEquals(fakeNote.note, storedNote.note)
        Assert.assertEquals(fakeNote.createdAt, storedNote.createdAt)
        Assert.assertEquals(fakeNote.updatedAt, storedNote.updatedAt)
    }

    @Test
    fun `after deleteNote(X), expects not find X in the Db`() = runTest {
        //Include X
        safeNotesRoomDataSource.saveNote(fakeNote)

        safeNotesRoomDataSource.deleteNote(fakeNote)

        val storedNote = (fakeDAO as FakeSafeNotesDAO).fakeDb.find { it.uid == fakeNote.uid }

        Assert.assertNull(storedNote)
    }

    @Test
    fun `given a DB with X, Y, Z, after deleteNote(X), expects to find only Y and Z in the Db`() =
        runTest {
            //Include X
            val x = fakeNote.copy(uid = 1)
            val y = fakeNote.copy(uid = 2)
            val z = fakeNote.copy(uid = 3)

            safeNotesRoomDataSource.saveNote(x)
            safeNotesRoomDataSource.saveNote(y)
            safeNotesRoomDataSource.saveNote(z)

            safeNotesRoomDataSource.deleteNote(x)

            val db = (fakeDAO as FakeSafeNotesDAO).fakeDb
            val storedNoteY = db.find { it.uid == y.uid }
            val storedNoteZ = db.find { it.uid == z.uid }
            val storedNoteX = db.find { it.uid == x.uid }

            Assert.assertNull(storedNoteX)
            Assert.assertNotNull(storedNoteY)
            Assert.assertNotNull(storedNoteZ)
        }

    @Test
    fun `given a DB with X, Y, Z, expects getSafeNotesFlow to return a flow with X, Y, Z type SafeNote`() =
        runTest {
            //Include X
            val x = fakeNote.copy(uid = 1)
            val y = fakeNote.copy(uid = 2)
            val z = fakeNote.copy(uid = 3)

            safeNotesRoomDataSource.saveNote(x)
            safeNotesRoomDataSource.saveNote(y)
            safeNotesRoomDataSource.saveNote(z)

            val list = safeNotesRoomDataSource.getSafeNotesFlow().first()

            Assert.assertEquals(x.uid, list[0].uid)
            Assert.assertEquals(y.uid, list[1].uid)
            Assert.assertEquals(z.uid, list[2].uid)

            Assert.assertTrue(list[0] is SafeNote)
            Assert.assertTrue(list[1] is SafeNote)
            Assert.assertTrue(list[2] is SafeNote)
        }

}