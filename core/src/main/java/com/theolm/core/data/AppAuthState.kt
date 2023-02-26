package com.theolm.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

interface AppAuthState {
    val lockState: Flow<LockState>
    suspend fun lock()
    suspend fun unlock()
    suspend fun bypassLockOnce()
}

class AppAuthStateImpl @Inject constructor() : AppAuthState {
    private val mutex = Mutex()
    private var preventLock = AtomicBoolean(false)
    override val lockState = MutableStateFlow(LockState.LOCK)

    override suspend fun lock() = mutex.withLock {
        if (preventLock.compareAndSet(true, false)) {
            return
        }

        lockState.emit(LockState.LOCK)
    }

    override suspend fun unlock() = mutex.withLock {
        lockState.emit(LockState.UNLOCK)
    }

    override suspend fun bypassLockOnce() = mutex.withLock {
        preventLock.set(true)
    }
}

enum class LockState { LOCK, UNLOCK }