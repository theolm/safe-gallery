package com.theolm.safeGallery.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theolm.safeGallery.framework.database.dao.SafeMessagesDAO
import com.theolm.safeGallery.framework.database.entities.SafeMessageEntity

@Database(entities = [SafeMessageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun safeMessagesDAO(): SafeMessagesDAO
}
