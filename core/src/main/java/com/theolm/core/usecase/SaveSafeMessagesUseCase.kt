package com.theolm.core.usecase

import com.theolm.core.data.SafeMessage
import com.theolm.core.repository.SafeMessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SaveSafeMessagesUseCase {
    suspend fun save(message: SafeMessage)
}

internal class SaveSafeMessagesUseCaseImpl @Inject constructor(
    private val repository: SafeMessagesRepository
) : SaveSafeMessagesUseCase {
    override suspend fun save(message: SafeMessage) {
        repository.saveMessage(message)
    }
}
