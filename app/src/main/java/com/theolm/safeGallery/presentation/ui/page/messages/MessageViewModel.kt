package com.theolm.safeGallery.presentation.ui.page.messages

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
class MessageViewModel @Inject constructor(
    getSafeMessagesUseCase: GetSafeMessagesUseCase,
    private val saveSafeMessagesUseCase: SaveSafeMessagesUseCase,
    private val deleteSafeMessagesUseCase: DeleteSafeMessagesUseCase,
) : ViewModel() {
    val messageFlow = getSafeMessagesUseCase.getFlow()
    var uiState by mutableStateOf(MessagePageUiState())

    fun onUpdateMessage(message: String) {
        uiState = uiState.copy(inputMessage = message)
    }

    fun onSaveMessage() {
        viewModelScope.launch {

            buildSageMessage(
                uiState.inputMessage,
                uiState.editingMessage,
            ).also {
                saveSafeMessagesUseCase.save(it)
            }

            uiState = MessagePageUiState()
        }
    }

    fun onMessageClick(index: Int) {
        val selectedIndex =
            if (uiState.expandedMessage == index) {
                -1
            } else {
                index
            }

        uiState = uiState.copy(expandedMessage = selectedIndex)
    }

    fun openAlertForMessage(message: SafeMessage) {
        uiState = uiState.copy(openOptionAlert = message)
    }

    fun closeAlert() {
        uiState = uiState.copy(openOptionAlert = null)
    }

    /**
     * This method is trigger when the user clicks on edit message button.
     * It simple puts the ui in an edit place.
     *
     * this is temporary. In the future this will probably send the user
     * to a new screen with more options.
     */
    fun onEditMessage(message: SafeMessage) {
        uiState = uiState.copy(
            inputMessage = message.message,
            editingMessage = message,
            openOptionAlert = null,
        )
    }

    fun deleteMessage(message: SafeMessage) {
        viewModelScope.launch {
            deleteSafeMessagesUseCase.delete(message)
            uiState = MessagePageUiState()
        }
    }

    private fun buildSageMessage(newText: String, editingMessage: SafeMessage?): SafeMessage {
        val date = Date().time
        return editingMessage?.copy(
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
 * This data class represents the UI state of the Message Page at a given time.
 *
 * @param inputMessage holds the message that is been typed by the user
 * @param expandedMessage holds the index of the message that is in the expanded state.
 * Only one message can be expanded at a give time. negative number means that there is no message expanded.
 * @param openOptionAlert represents if the option alert dialog is shown or not.
 * If it's null the alert not visible, otherwise is visible and holds the reference of the message that triggered the alert.
 * @param editingMessage holds reference of to the message that is in edit mode. If is null then its in new message mode.
 */
data class MessagePageUiState(
    val inputMessage: String = "",
    val expandedMessage: Int = -1,
    val openOptionAlert: SafeMessage? = null,
    val editingMessage: SafeMessage? = null,
)
