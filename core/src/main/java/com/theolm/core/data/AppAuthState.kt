package com.theolm.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface AppAuthState {
    val lockState : Flow<LockState>
    suspend fun lock()
    suspend fun unlock()
}

class AppAuthStateImpl @Inject constructor() : AppAuthState {
    override val lockState = MutableStateFlow(LockState.LOCK)

    override suspend fun lock() {
        lockState.emit(LockState.LOCK)
    }

    override suspend fun unlock() {
        lockState.emit(LockState.UNLOCK)
    }
}

enum class LockState { LOCK, UNLOCK }