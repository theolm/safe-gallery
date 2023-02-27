package com.theolm.safeGallery.framework.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theolm.core.data.SafeNote

@Entity
data class SafeNoteEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val title: String,
    val note: String,
    val createdAt: Long,
    val updatedAt: Long,
) {
    fun toSafeNote() =
        SafeNote(
            uid = uid,
            title = title,
            note = note,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

    companion object {
        fun fromSafeNote(note: SafeNote) = with(note) {
            SafeNoteEntity(
                uid = uid ?: 0,
                title = title,
                note = this.note,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
