package com.theolm.safeGallery.presentation.ui.page.editNote

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.core.data.SafeNote
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class EditNotePageNavArgs(
    val safeNote: SafeNote? = null
)

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
    val lazyState = rememberLazyListState()

    val topBarElevation by animateDpAsState(
        if (lazyState.hasOffset()) 6.dp else 0.dp
    )

    KeyboardManager(viewModel.keyboardEventState)

    LaunchedEffect(uiState.isEditMode) {
        if (uiState.isEditMode) {
            /* Workaround for the issue https://issuetracker.google.com/issues/204502668?pli=1 */
            delay(100)
            focusRequester.requestFocus()
        } else {
            viewModel.keyboardEventState.closeKeyboard()
        }
    }

    DeleteDialog(viewModel) { navigator.popBackStack() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        snackbarHost = { SnackbarHost(viewModel.snackBarHostState) },
        topBar = {
            EditNoteTopBar(
                viewModel = viewModel,
                navigator = navigator,
                elevation = topBarElevation
            )
        },
    ) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        LazyColumn(
            modifier = Modifier.imePadding(),
            state = lazyState,
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding() + screenHeight.times(0.3f)
            )
        ) {
            item {
                CustomTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    value = uiState.title,
                    onValueChange = viewModel::onTitleChange,
                    singleLine = false,
                    enabled = uiState.isEditMode,
                    readOnly = !uiState.isEditMode,
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = true,
                    ),
                    placeholder = stringResource(id = R.string.message_title)
                )
            }

            item {
                CustomTextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .focusRequester(focusRequester),
                    value = uiState.note,
                    onValueChange = viewModel::onNoteChange,
                    singleLine = false,
                    readOnly = !uiState.isEditMode,
                    enabled = uiState.isEditMode,
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = true,
                    ),
                    placeholder = stringResource(id = R.string.placeholder_note_input)
                )
            }
        }
    }

    val closeEvent by viewModel.closeEvent.collectAsState(initial = false)
    if (closeEvent) {
        navigator.popBackStack()
    }
}

@Composable
private fun DeleteDialog(
    viewModel: EditNoteViewModel,
    onDelete: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    ConfirmationAlertDialog(
        showAlert = viewModel.uiState.showDeleteAlert,
        title = stringResource(id = R.string.delete_note),
        message = stringResource(id = R.string.delete_note_message),
        confirmButton = stringResource(id = R.string.delete),
        confirmButtonColor = MaterialTheme.colorScheme.error,
        cancelButton = stringResource(id = R.string.cancel),
        onDismiss = viewModel::closeDeleteAlert,
        onConfirm = {
            scope.launch {
                viewModel.onDeleteNote()
                onDelete.invoke()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditNoteTopBar(
    viewModel: EditNoteViewModel,
    navigator: DestinationsNavigator,
    elevation: Dp,
) {
    val uiState = viewModel.uiState
    val showSaveButton = uiState.isNewNote || uiState.isEditMode
    val showEditButton = !uiState.isNewNote && !uiState.isEditMode
    val showDeleteButton = !uiState.isNewNote

    Surface(shadowElevation = elevation) {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { navigator.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            actions = {
                if (showSaveButton) {
                    IconButton(onClick = viewModel::saveNote) {
                        Icon(
                            imageVector = Icons.Outlined.Save,
                            contentDescription = stringResource(id = R.string.save_note)
                        )
                    }
                }

                if (showEditButton) {
                    IconButton(onClick = viewModel::startEditing) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(id = R.string.edit_note)
                        )
                    }
                }

                if (showDeleteButton) {
                    IconButton(onClick = viewModel::startDeleting) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(id = R.string.delete_note)
                        )
                    }
                }
            }
        )
    }
}