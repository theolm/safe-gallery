package com.theolm.core.usecase

import com.theolm.core.data.SafeMessage
import com.theolm.core.repository.SafeMessagesRepository
import javax.inject.Inject

interface DeleteSafeMessagesUseCase {
    suspend fun delete(safeMessage: SafeMessage)
}

internal class DeleteSafeMessagesUseCaseImpl @Inject constructor(
    private val repository: SafeMessagesRepository
) : DeleteSafeMessagesUseCase{
    override suspend fun delete(safeMessage: SafeMessage) {
        repository.deleteMessage(safeMessage)
    }

}
