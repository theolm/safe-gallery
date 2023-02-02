package com.theolm.core.di

import com.theolm.core.data.AppAuthState
import com.theolm.core.data.AppAuthStateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindsAppAuthState(state: AppAuthStateImpl) : AppAuthState
}