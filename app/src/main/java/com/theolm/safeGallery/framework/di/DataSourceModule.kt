package com.theolm.safeGallery.framework.di

import com.theolm.core.repository.MessageDataSource
import com.theolm.safeGallery.framework.database.source.SafeMessageRoomDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindsSafeMessageRoomDataSource(dataSource: SafeMessageRoomDataSource): MessageDataSource
}
