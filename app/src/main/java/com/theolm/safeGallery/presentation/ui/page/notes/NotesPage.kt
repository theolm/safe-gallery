package com.theolm.safeGallery.presentation.ui.page.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.theolm.safeGallery.presentation.ui.page.notes.component.NoteBubble
import com.theolm.safeGallery.presentation.ui.components.OptionsAlertDialog
import com.theolm.safeGallery.presentation.ui.components.OptionsAlertItem
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.BottomNavigationHeight
import com.theolm.safeGallery.presentation.ui.components.isScrollingUp
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun NotesPage(viewModel: NotesViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val noteList by viewModel.notesFlow.collectAsState(initial = listOf())
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val listState = rememberLazyListState()

    NotesOptionsDialog(viewModel)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        backgroundColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = listState.isScrollingUp(),
                icon = {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = null
                    )
                },
                text = {
                    Text("ADD NOTE")
                },
                onClick = { /*do something*/ }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            state = listState,
            contentPadding = PaddingValues(top = 32.dp, bottom = (screenHeightDp / 2).dp)
        ) {
            itemsIndexed(noteList) { index, it ->
                NoteBubble(
                    modifier = Modifier.fillMaxWidth(),
                    note = it.message,
                    lastModified = Date(it.updatedAt),
                    isExpanded = uiState.expandedNote == index,
                    onClick = {
                        viewModel.onNoteClick(index)
                    },
                    onLongClick = {
                        viewModel.onNoteClick(index)
                        viewModel.openAlertForNote(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun NotesOptionsDialog(viewModel: NotesViewModel) {
    viewModel.uiState.openOptionAlert?.let { message ->
        OptionsAlertDialog(
            OptionsAlertItem(
                text = stringResource(id = R.string.edit_note),
                icon = Icons.Default.Edit,
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                    viewModel.onEditNote(message)
                }
            ),
            OptionsAlertItem(
                text = stringResource(id = R.string.delete_note),
                icon = Icons.Default.Delete,
                color = MaterialTheme.colorScheme.error,
                onClick = {
                    viewModel.deleteNote(message)
                }
            ),
            onDismiss = {
                viewModel.closeAlert()
            }
        )
    }
}