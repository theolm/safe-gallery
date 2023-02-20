package com.theolm.core.di

import com.theolm.core.usecase.*
import com.theolm.core.usecase.GetSafeNotesUseCaseImpl
import com.theolm.core.usecase.SaveSafeNoteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
internal abstract class UseCaseModule {
    @Binds
    internal abstract fun bindsSaveNote(useCase: SaveSafeNoteUseCaseImpl): SaveSafeNoteUseCase

    @Binds
    internal abstract fun bindsGetNote(useCase: GetSafeNotesUseCaseImpl): GetSafeNotesUseCase

    @Binds
    internal abstract fun bindsDeleteNote(useCase: DeleteSafeNoteUseCaseImpl): DeleteSafeNoteUseCase

    @Binds
    internal abstract fun bindsCreateTempFile(useCase: CreateTempFileUseCaseImpl): CreateTempFileUseCase

    @Binds
    internal abstract fun bindsSavePhoto(useCase: SavePhotoUseCaseImpl): SavePhotoUseCase

    @Binds
    internal abstract fun bindsDeletePhoto(useCase: DeletePhotoUseCaseImpl): DeletePhotoUseCase

    @Binds
    internal abstract fun bindsGetPhoto(useCase: GetPhotosUseCaseImpl): GetPhotosUseCase
}
