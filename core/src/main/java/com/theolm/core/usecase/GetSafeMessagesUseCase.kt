package com.theolm.core.usecase

import com.theolm.core.data.SafeMessage
import com.theolm.core.repository.SafeMessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetSafeMessagesUseCase {
    fun getFlow(): Flow<List<SafeMessage>>
}

internal class GetSafeMessagesUseCaseImpl @Inject constructor(
    private val repository: SafeMessagesRepository
) : GetSafeMessagesUseCase{
    override fun getFlow(): Flow<List<SafeMessage>> =
        repository.getSafeMessagesFlow()
}
