package com.theolm.safeGallery.presentation.ui.page.editNote.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.ToolBar
import com.theolm.safeGallery.presentation.ui.components.ToolBarAction
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

    ToolBar(
        title = "Message title",
        onBackPress = onBackPress,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        actions = {
            //TODO: change animation to fadeIn/fadeOut

            AnimatedVisibility(visible = showSaveButton) {
                ToolBarAction(
                    icon = Icons.Outlined.Save,
                    onClick = onSaveClick,
                    contentDescription = stringResource(id = R.string.save_note)
                )
            }

            AnimatedVisibility(visible = showEditButton) {
                ToolBarAction(
                    icon = Icons.Outlined.Edit,
                    onClick = onEditClick,
                    contentDescription = stringResource(id = R.string.edit_note)
                )
            }

            AnimatedVisibility(visible = showDeleteButton) {
                ToolBarAction(
                    icon = Icons.Outlined.Delete,
                    onClick = onDeleteClick,
                    contentDescription = stringResource(id = R.string.delete_note)
                )
            }
        }
    )
}
