package com.theolm.safeGallery.framework.database.source

import com.theolm.core.data.SafeMessage
import com.theolm.core.repository.MessageDataSource
import com.theolm.safeGallery.framework.database.dao.SafeMessagesDAO
import com.theolm.safeGallery.framework.database.entities.SafeMessageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SafeMessageRoomDataSource @Inject constructor(
    private val messagesDAO: SafeMessagesDAO
) : MessageDataSource {
    override suspend fun saveMessage(message: SafeMessage) {
        messagesDAO.insertMessage(SafeMessageEntity.fromSafeMessage(message))
    }

    override fun getSafeMessagesFlow(): Flow<List<SafeMessage>> = messagesDAO.getAll().map { list ->
        list.map { it.toSafeMessage() }
    }
}