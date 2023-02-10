package com.theolm.core.di

import com.theolm.core.repository.SafeNotesRepository
import com.theolm.core.repository.SafeNotesRepositoryImpl
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
    internal abstract fun bindsSafeNoteRepo(repoImpl: SafeNotesRepositoryImpl): SafeNotesRepository
}
