package com.theolm.safeGallery.presentation.ui.page.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theolm.core.data.SafeMessage
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
) : ViewModel() {
    val messageFlow = getSafeMessagesUseCase.getFlow()
    var uiState by mutableStateOf(MessagePageUiState())

    fun onUpdateMessage(message: String) {
        uiState = uiState.copy(inputMessage = message)
    }

    fun onSaveMessage() {
        viewModelScope.launch {
            val newMessage = uiState.inputMessage
            val date = Date().time
            saveSafeMessagesUseCase.save(
                SafeMessage(
                    message = newMessage,
                    createdAt = date,
                    updatedAt = date,
                )
            )

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
}

data class MessagePageUiState(
    val inputMessage: String = "",
    val expandedMessage: Int = -1,
    val openOptionAlert: SafeMessage? = null,
)
