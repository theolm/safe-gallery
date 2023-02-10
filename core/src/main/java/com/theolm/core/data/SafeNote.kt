package com.theolm.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class SafeNote(
    val uid: Int? = null,
    val note: String,
    val createdAt: Long, //Timestamp
    val updatedAt: Long, //Timestamp
) : Parcelable {
    companion object {
        fun default() = SafeNote(
            note = "",
            createdAt = Date().time,
            updatedAt = Date().time,
        )
    }
}
