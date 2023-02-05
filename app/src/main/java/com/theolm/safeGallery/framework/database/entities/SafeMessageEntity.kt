package com.theolm.safeGallery.framework.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theolm.core.data.SafeMessage

@Entity
data class SafeMessageEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val message: String,
    val createdAt: Long,
    val updatedAt: Long,
) {
    fun toSafeMessage() =
        SafeMessage(
            uid = uid,
            message = message,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

    companion object {
        fun fromSafeMessage(msg: SafeMessage) = with(msg) {
            SafeMessageEntity(
                uid = uid ?: 0,
                message = message,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
