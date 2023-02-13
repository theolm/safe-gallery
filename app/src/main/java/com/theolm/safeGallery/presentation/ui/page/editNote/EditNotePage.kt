package com.theolm.safeGallery.presentation.ui.page.editNote

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.core.data.SafeNote
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.page.editNote.components.EditNoteTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination(navArgsDelegate = EditNotePageNavArgs::class)
@Composable
fun EditNotePage(
    navigator: DestinationsNavigator,
    viewModel: EditNoteViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val scroll = rememberScrollState()

    LaunchedEffect(uiState.isEditMode) {
        if (uiState.isEditMode) {
            /* Workaround for the issue https://issuetracker.google.com/issues/204502668?pli=1 */
            delay(100)
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        topBar = {
            val scope = rememberCoroutineScope()
            EditNoteTopBar(
                uiState = uiState,
                scrollBehavior = scrollBehavior,
                onBackPress = {
                    navigator.popBackStack()
                },
                onSaveClick = {
                    scope.launch {
                        val saved = viewModel.saveNote()
                        if (saved) navigator.popBackStack()
                    }
                },
                onEditClick = { viewModel.startEditing() },
                onDeleteClick = { viewModel.startDeleting() }
            )
        },
    ) {
        //TODO: change the value from String to TextFieldValue
        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                .verticalScroll(scroll)
                .focusRequester(focusRequester),
            value = uiState.note.text,
            onValueChange = viewModel::onNoteChange,
            singleLine = false,
            readOnly = !uiState.isEditMode,
            textStyle = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                if (uiState.note.text.isEmpty()) {
                    Placeholder()
                } else {
                    innerTextField()
                }
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
            ),
        )
    }
}

data class EditNotePageNavArgs(
    val safeNote: SafeNote? = null
)

@Composable
private fun Placeholder() {
    Text(
        text = stringResource(id = R.string.placeholder_note_input),
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    )
}
