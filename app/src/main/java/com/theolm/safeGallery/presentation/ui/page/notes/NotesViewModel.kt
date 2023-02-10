package com.theolm.safeGallery.presentation.ui.page.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.core.data.SafeMessage
import com.theolm.core.usecase.DeleteSafeMessagesUseCase
import com.theolm.core.usecase.GetSafeMessagesUseCase
import com.theolm.core.usecase.SaveSafeMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    getSafeNotesUseCase: GetSafeMessagesUseCase,
    private val saveSafeNotesUseCase: SaveSafeMessagesUseCase,
    private val deleteSafeNotesUseCase: DeleteSafeMessagesUseCase,
) : ViewModel() {
    val notesFlow = getSafeNotesUseCase.getFlow()
    var uiState by mutableStateOf(NotesPageUiState())

    fun onUpdateNote(note: String) {
        uiState = uiState.copy(inputNote = note)
    }

    fun onSaveNote() {
        viewModelScope.launch {

            buildSafeNote(
                uiState.inputNote,
                uiState.editingNote,
            ).also {
                saveSafeNotesUseCase.save(it)
            }

            uiState = NotesPageUiState()
        }
    }

    fun onNoteClick(index: Int) {
        val selectedIndex =
            if (uiState.expandedNote == index) {
                -1
            } else {
                index
            }

        uiState = uiState.copy(expandedNote = selectedIndex)
    }

    fun openAlertForNote(note: SafeMessage) {
        uiState = uiState.copy(openOptionAlert = note)
    }

    fun closeAlert() {
        uiState = uiState.copy(openOptionAlert = null)
    }

    /**
     * This method is trigger when the user clicks on edit note button.
     * It simple puts the ui in an edit place.
     *
     * this is temporary. In the future this will probably send the user
     * to a new screen with more options.
     */
    fun onEditNote(note: SafeMessage) {
        uiState = uiState.copy(
            inputNote = note.message,
            editingNote = note,
            openOptionAlert = null,
        )
    }

    fun deleteNote(note: SafeMessage) {
        viewModelScope.launch {
            deleteSafeNotesUseCase.delete(note)
            uiState = NotesPageUiState()
        }
    }

    private fun buildSafeNote(newText: String, editingNote: SafeMessage?): SafeMessage {
        val date = Date().time
        return editingNote?.copy(
            message = newText,
            updatedAt = date,
        ) ?: run {
            SafeMessage(
                message = newText,
                createdAt = date,
                updatedAt = date,
            )
        }
    }
}

/**
 * This data class represents the UI state of the Notes Page at a given time.
 *
 * @param inputNote holds the note that is been typed by the user
 * @param expandedNote holds the index of the note that is in the expanded state.
 * Only one note can be expanded at a give time. negative number means that there is no note expanded.
 * @param openOptionAlert represents if the option alert dialog is shown or not.
 * If it's null the alert not visible, otherwise is visible and holds the reference of the note that triggered the alert.
 * @param editingNote holds reference of to the note that is in edit mode. If is null then its in new note mode.
 */
data class NotesPageUiState(
    val inputNote: String = "",
    val expandedNote: Int = -1,
    val openOptionAlert: SafeMessage? = null,
    val editingNote: SafeMessage? = null,
)
