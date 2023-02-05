package com.theolm.core.di

import com.theolm.core.usecase.GetSafeMessagesUseCase
import com.theolm.core.usecase.GetSafeMessagesUseCaseImpl
import com.theolm.core.usecase.SaveSafeMessagesUseCase
import com.theolm.core.usecase.SaveSafeMessagesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class UseCaseModule {
    @Binds
    internal abstract fun bindsSaveMessage(useCase: SaveSafeMessagesUseCaseImpl): SaveSafeMessagesUseCase

    @Binds
    internal abstract fun bindsGetMessage(useCase: GetSafeMessagesUseCaseImpl): GetSafeMessagesUseCase
}
