package com.theolm.safeGallery.presentation.ui.page.editNote.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.page.editNote.EditNoteUiState
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeDark
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeLight
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewLight() {
    PreviewThemeLight {
        EditNoteTopBar(EditNoteUiState())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewDark() {
    PreviewThemeDark {
        EditNoteTopBar(EditNoteUiState())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteTopBar(
    uiState: EditNoteUiState,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onBackPress: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    val showSaveButton = uiState.isNewNote || uiState.isEditMode
    val showEditButton = !uiState.isNewNote && !uiState.isEditMode
    val showDeleteButton = !uiState.isNewNote

    LargeTopAppBar(
        title = {
            Text(text = "Message title")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior,
        actions = {
            AnimatedVisibility(
                visible = showSaveButton,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                IconButton(onClick = onSaveClick) {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = stringResource(id = R.string.save_note)
                    )
                }
            }

            AnimatedVisibility(
                visible = showEditButton,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = stringResource(id = R.string.edit_note)
                    )
                }
            }

            AnimatedVisibility(
                visible = showDeleteButton,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(id = R.string.delete_note)
                    )
                }
            }
        },
    )
}
