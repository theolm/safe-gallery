package com.theolm.safeGallery.presentation.ui.page.notes

import androidx.lifecycle.ViewModel
import com.theolm.core.usecase.GetSafeNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    getSafeNotesUseCase: GetSafeNotesUseCase,
) : ViewModel() {
    val notesFlow = getSafeNotesUseCase().map {
        it.sortedByDescending { it.updatedAt }
    }
}
