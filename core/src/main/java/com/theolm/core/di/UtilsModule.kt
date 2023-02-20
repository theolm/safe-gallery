package com.theolm.core.di

import com.theolm.core.utils.AppFileUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class UtilsModule {
    @Singleton
    @Provides
    fun providesAppFileUtils() : AppFileUtils = AppFileUtils

}