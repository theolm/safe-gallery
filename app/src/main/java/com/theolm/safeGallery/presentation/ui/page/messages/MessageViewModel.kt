package com.theolm.safeGallery.presentation.ui.page.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(MessagePageUiState())

    fun onUpdateMessage(message: String) {
        uiState = uiState.copy(message = message)
    }

    fun onSaveMessage() {
        val newMessage = uiState.message
        uiState = uiState.copy(
            messageList = uiState.messageList.toMutableList().apply { add(newMessage) }
        )
    }
}

data class MessagePageUiState(
    val message: String = "",
    val messageList: List<String> = listOf()
)