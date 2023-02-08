package com.theolm.core.repository

import com.theolm.core.data.SafeMessage
import kotlinx.coroutines.flow.Flow

interface MessageDataSource {
    suspend fun saveMessage(message : SafeMessage)

    suspend fun deleteMessage(message: SafeMessage)

    fun getSafeMessagesFlow() : Flow<List<SafeMessage>>
}