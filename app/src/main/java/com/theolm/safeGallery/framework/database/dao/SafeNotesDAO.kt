package com.theolm.safeGallery.framework.database.dao

import androidx.room.*
import com.theolm.safeGallery.framework.database.entities.SafeNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SafeNotesDAO {
    @Query("SELECT * FROM SafeNoteEntity")
    fun getAll(): Flow<List<SafeNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: SafeNoteEntity)

    @Delete
    suspend fun deleteNote(note: SafeNoteEntity)
}
