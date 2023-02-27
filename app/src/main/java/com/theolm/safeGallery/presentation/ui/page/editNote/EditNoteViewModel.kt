package com.theolm.safeGallery.presentation.ui.page.editNote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.core.data.SafeNote
import com.theolm.core.usecase.DeleteSafeNoteUseCase
import com.theolm.core.usecase.SaveSafeNoteUseCase
import com.theolm.safeGallery.presentation.ui.page.destinations.EditNotePageDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val saveSafeNotesUseCase: SaveSafeNoteUseCase,
    private val deleteSafeNoteUseCase: DeleteSafeNoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val safeNote: SafeNote
    val closeEvent = MutableStateFlow(false)
    var uiState by mutableStateOf(EditNoteUiState())
        private set

    init {
        val initNote = EditNotePageDestination.argsFrom(savedStateHandle).safeNote
        if (initNote == null) {
            safeNote = SafeNote.default()
            uiState = uiState.copy(
                title = TextFieldValue(safeNote.title),
                note = TextFieldValue(safeNote.note),
                isNewNote = true,
                isEditMode = true
            )
        } else {
            safeNote = initNote
            uiState = uiState.copy(
                title = TextFieldValue(safeNote.title),
                note = TextFieldValue(safeNote.note),
                isNewNote = false,
                isEditMode = false
            )
        }
    }

    fun onNoteChange(value: String) {
        uiState = uiState.copy(note = TextFieldValue(value))
    }

    fun onTitleChange(value: String) {
        uiState = uiState.copy(title = TextFieldValue(value))
    }

    fun startEditing() {
        uiState = uiState.copy(isEditMode = true)
    }

    fun startDeleting() {
        uiState = uiState.copy(showDeleteAlert = true)
    }

    suspend fun onDeleteNote() {
        closeDeleteAlert()
        deleteSafeNoteUseCase(safeNote)
    }

    fun closeDeleteAlert() {
        uiState = uiState.copy(showDeleteAlert = false)
    }

    fun saveNote() {
        viewModelScope.launch {
            uiState = uiState.copy(isEditMode = false)

            if (uiState.note.text.isBlank() || uiState.title.text.isBlank()) {
                // TODO: show Snackbar error
                return@launch
            }

            safeNote.copy(
                title = uiState.title.text,
                note = uiState.note.text,
                updatedAt = Date().time
            ).also {
                saveSafeNotesUseCase(it)
                closeEvent.emit(true)
            }
        }
    }
}

data class EditNoteUiState(
    val title: TextFieldValue = TextFieldValue(),
    val note: TextFieldValue = TextFieldValue(),
    val isNewNote: Boolean = false,
    val isEditMode: Boolean = false,
    val showDeleteAlert: Boolean = false,
)