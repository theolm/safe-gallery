package com.theolm.safeGallery.presentation.ui.page.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.BottomNavigationHeight
import com.theolm.safeGallery.presentation.ui.page.destinations.EditNotePageDestination
import com.theolm.safeGallery.presentation.ui.page.editNote.EditNotePageNavArgs
import com.theolm.safeGallery.presentation.ui.page.notes.component.NoteBubble
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun NotesPage(
    navigator: DestinationsNavigator,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val noteList by viewModel.notesFlow.collectAsState(initial = listOf())
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.note)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = {
                            navigator.navigate(EditNotePageDestination())
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.NoteAdd,
                            contentDescription = stringResource(id = R.string.add_note)
                        )
                    }
                }
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            state = listState,
            contentPadding = PaddingValues(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = 0.dp,
                end = 0.dp
            )
        ) {
            items(noteList) {
                NoteBubble(
                    modifier = Modifier.fillMaxWidth(),
                    title = it.title,
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
