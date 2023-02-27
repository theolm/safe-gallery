package com.theolm.safeGallery.framework.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.theolm.safeGallery.framework.database.dao.SafeNotesDAO
import com.theolm.safeGallery.framework.database.entities.SafeNoteEntity

@Database(
    version = 2,
    entities = [SafeNoteEntity::class],
    autoMigrations = [AutoMigration (from = 1, to = 2)]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun safeNotesDAO(): SafeNotesDAO
}
