package com.theolm.safeGallery.framework.database.dao

import androidx.room.*
import com.theolm.safeGallery.framework.database.entities.SafeMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SafeMessagesDAO {
    @Query("SELECT * FROM SafeMessageEntity")
    fun getAll(): Flow<List<SafeMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: SafeMessageEntity)

    @Delete
    suspend fun deleteMessage(message: SafeMessageEntity)
}
