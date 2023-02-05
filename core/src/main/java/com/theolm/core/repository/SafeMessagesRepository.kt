package com.theolm.core.repository

import com.theolm.core.data.SafeMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SafeMessagesRepository {
    suspend fun saveMessage(message: SafeMessage)
    fun getSafeMessagesFlow(): Flow<List<SafeMessage>>
}

internal class SafeMessagesRepositoryImpl @Inject constructor(
    private val dataSource: MessageDataSource
) :
    SafeMessagesRepository {
    override suspend fun saveMessage(message: SafeMessage) {
        dataSource.saveMessage(message)
    }

    override fun getSafeMessagesFlow(): Flow<List<SafeMessage>> = dataSource.getSafeMessagesFlow()
}
