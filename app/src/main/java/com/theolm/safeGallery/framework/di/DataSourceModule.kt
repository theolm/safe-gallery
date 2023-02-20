package com.theolm.safeGallery.framework.di

import com.theolm.core.repository.GalleryDataSource
import com.theolm.core.repository.NotesDataSource
import com.theolm.safeGallery.framework.database.source.SafeNotesRoomDataSource
import com.theolm.safeGallery.framework.gallery.SafeGalleryDataSource
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
    abstract fun bindsSafeNotesRoomDataSource(dataSource: SafeNotesRoomDataSource): NotesDataSource

    @Singleton
    @Binds
    abstract fun bindsSafeGalleryDataSource(dataSource: SafeGalleryDataSource): GalleryDataSource
}
