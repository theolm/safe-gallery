package com.theolm.safeGallery.presentation.ui.page.messages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.safeGallery.presentation.ui.component.MessageBubble
import com.theolm.safeGallery.presentation.ui.component.MessageInput
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun MessagePage(
    navigator: DestinationsNavigator,
    viewModel: MessageViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val messageList by viewModel.messageFlow.collectAsState(initial = listOf())
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        backgroundColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            MessageInput(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                message = uiState.inputMessage,
                onMessageChange = {
                    viewModel.onUpdateMessage(it)
                },
                onSaveClick = {
                    viewModel.onSaveMessage()
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(top = 32.dp, bottom = 100.dp)
        ) {
            itemsIndexed(messageList) { index, it ->
                MessageBubble(
                    modifier = Modifier.fillMaxWidth(),
                    message = it.message,
                    lastModified = Date(it.updatedAt),
                    isExpanded = uiState.expandedMessage == index,
                    onClick = {
                        viewModel.onMessageClick(index)
                    }
                )
            }
        }
    }
}