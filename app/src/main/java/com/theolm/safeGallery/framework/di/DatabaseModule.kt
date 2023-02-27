package com.theolm.safeGallery.framework.di

import android.content.Context
import androidx.room.Room
import com.theolm.safeGallery.framework.database.AppDatabase
import com.theolm.safeGallery.framework.database.dao.SafeNotesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideSafeNotesDao(appDatabase: AppDatabase): SafeNotesDAO {
        return appDatabase.safeNotesDAO()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "SafeGalleryDB")
            .build()
    }
}
