package com.theolm.biometric.di

import com.theolm.biometric.BiometricAuth
import com.theolm.biometric.BiometricAuthImpl
import com.theolm.biometric.useCase.AuthenticateUseCase
import com.theolm.biometric.useCase.AuthenticateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class AuthModule {
    @Singleton
    @Binds
    internal abstract fun bindsBiometricAuth(auth: BiometricAuthImpl): BiometricAuth
}

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class AuthUseCaseModule {

    @Binds
    internal abstract fun bindsAuthUseCase(useCase: AuthenticateUseCaseImpl): AuthenticateUseCase
}