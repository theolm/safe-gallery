package com.theolm.safeGallery.presentation.ui.page.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.safeGallery.presentation.ui.components.BottomNavigationHeight
import com.theolm.safeGallery.presentation.ui.components.isScrollingUp
import com.theolm.safeGallery.presentation.ui.page.destinations.EditNotePageDestination
import com.theolm.safeGallery.presentation.ui.page.editNote.EditNotePageNavArgs
import com.theolm.safeGallery.presentation.ui.page.notes.component.NoteBubble
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun NotesPage(
    navigator: DestinationsNavigator,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val noteList by viewModel.notesFlow.collectAsState(initial = listOf())
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val listState = rememberLazyListState()

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
                onClick = {
                    navigator.navigate(EditNotePageDestination())
                }
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
            items(noteList) {
                NoteBubble(
                    modifier = Modifier.fillMaxWidth(),
                    note = it.note,
                    lastModified = Date(it.updatedAt),
                    onClick = {
                        navigator.navigate(EditNotePageDestination(navArgs = EditNotePageNavArgs(it)))
                    },
                )
            }
        }
    }
}
