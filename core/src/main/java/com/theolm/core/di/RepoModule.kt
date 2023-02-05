package com.theolm.core.di

import com.theolm.core.repository.SafeMessagesRepository
import com.theolm.core.repository.SafeMessagesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RepoModule {
    @Singleton
    @Binds
    internal abstract fun bindsSafeMessageRepo(repoImpl: SafeMessagesRepositoryImpl): SafeMessagesRepository
}
