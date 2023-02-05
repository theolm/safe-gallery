package com.theolm.core.data

data class SafeMessage(
    val uid: Int? = null,
    val message: String,
    val createdAt: Long, //Timestamp
    val updatedAt: Long, //Timestamp
)