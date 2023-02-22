package com.theolm.core.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class AppAuthStateImplTest {
    lateinit var authState: AppAuthState

    @Before
    fun setUp() {
        authState = AppAuthStateImpl()
    }

    @Test
    fun `initial lockState should be LOCK`() =
        runTest {
            val current = authState.lockState.first()
            Assert.assertEquals(LockState.LOCK, current)
        }

    @Test
    fun `lockState after call unlock() should be UNLOCK`() =
        runTest {
            authState.unlock()
            Assert.assertEquals(LockState.UNLOCK, authState.lockState.first())
        }

    @Test
    fun `when lockState == UNLOCK, calling lock() expects to change the state lockState == LOCK`() =
        runTest {
            authState.unlock()
            authState.lock()
            Assert.assertEquals(LockState.LOCK, authState.lockState.first())
        }
}