package com.theolm.safeGallery.presentation.ui.page.editNote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.core.data.SafeNote
import com.theolm.core.usecase.SaveSafeNoteUseCase
import com.theolm.safeGallery.presentation.ui.page.destinations.EditNotePageDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val saveSafeNotesUseCase: SaveSafeNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val safeNote: SafeNote

    var uiState by mutableStateOf(EditNoteUiState())
        private set

    init {
        val initNote = EditNotePageDestination.argsFrom(savedStateHandle).safeNote
        safeNote = initNote ?: SafeNote.default()
        uiState = uiState.copy(
            note = safeNote.note,
            isEditMode = initNote == null
        )
    }

    fun onNoteChange(value: String) {
        uiState = uiState.copy(note = value)
    }

    fun startEditing() {
        uiState = uiState.copy(isEditMode = true)
    }

    fun saveNote() {
        viewModelScope.launch {
            uiState = uiState.copy(isEditMode = false)
            safeNote.copy(
                note = uiState.note,
                updatedAt = Date().time
            ).also {
                saveSafeNotesUseCase.save(it)
            }
        }
    }
}

data class EditNoteUiState(
    val note: String = "",
    val isEditMode: Boolean = false
)