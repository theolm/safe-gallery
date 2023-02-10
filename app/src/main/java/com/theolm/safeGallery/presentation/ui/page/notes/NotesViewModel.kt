package com.theolm.safeGallery.presentation.ui.page.notes

import androidx.lifecycle.ViewModel
import com.theolm.core.usecase.GetSafeNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    getSafeNotesUseCase: GetSafeNotesUseCase,
) : ViewModel() {
    val notesFlow = getSafeNotesUseCase.getFlow()
}
