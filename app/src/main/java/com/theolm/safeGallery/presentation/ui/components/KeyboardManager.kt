package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import kotlinx.coroutines.delay


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardManager(state: KeyboardManagerState) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.keyboardState) {
        when (state.keyboardState) {
            KeyboardEvent.Open -> keyboard?.show()
            KeyboardEvent.Close -> {
                focusManager.clearFocus()
                keyboard?.hide()
            }
            else -> Unit
        }
    }
}

class KeyboardManagerState {
    var keyboardState by mutableStateOf<KeyboardEvent?>(null)
        private set

    suspend fun closeKeyboard() {
        keyboardState = KeyboardEvent.Close
        reset()
    }

    suspend fun openKeyboard() {
        keyboardState = KeyboardEvent.Open
        reset()
    }

    //TODO: change delay for observable.
    private suspend fun reset() {
        delay(resetDelay)
        keyboardState = null
    }

    private companion object {
        const val resetDelay = 100L
    }
}

enum class KeyboardEvent { Open, Close }
