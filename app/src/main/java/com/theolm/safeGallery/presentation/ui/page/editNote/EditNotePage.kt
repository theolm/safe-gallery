package com.theolm.safeGallery.presentation.ui.page.editNote

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.core.data.SafeNote
import com.theolm.safeGallery.R

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

    LaunchedEffect(uiState.isEditMode) {
        if (uiState.isEditMode) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        floatingActionButton = {
            SaveFab(viewModel = viewModel)
        },
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(all = 16.dp)
                .focusRequester(focusRequester),
            value = uiState.note,
            onValueChange = viewModel::onNoteChange,
            singleLine = false,
            readOnly = !uiState.isEditMode,
            textStyle = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                if (uiState.note.isEmpty()) {
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

@Composable
private fun SaveFab(viewModel: EditNoteViewModel) {
    val context = LocalContext.current
    val isReadMode = !viewModel.uiState.isEditMode
    val icon = if (isReadMode) Icons.Outlined.Edit else Icons.Outlined.Save
    val textRes = if (isReadMode) R.string.edit_note else R.string.save_note

    ExtendedFloatingActionButton(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = textRes)
            )
        },
        text = {
            Text(stringResource(id = textRes))
        },
        onClick = {
            if (isReadMode) {
                viewModel.startEditing()
            } else {
                viewModel.saveNote()
                Toast.makeText(context, context.getText(R.string.note_saved), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    )
}