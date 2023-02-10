package com.theolm.safeGallery.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theolm.safeGallery.framework.database.dao.SafeNotesDAO
import com.theolm.safeGallery.framework.database.entities.SafeNoteEntity

@Database(entities = [SafeNoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun safeNotesDAO(): SafeNotesDAO
}
