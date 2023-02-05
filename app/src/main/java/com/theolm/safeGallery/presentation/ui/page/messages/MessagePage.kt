package com.theolm.safeGallery.presentation.ui.page.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theolm.safeGallery.presentation.ui.component.MessageBubble
import com.theolm.safeGallery.presentation.ui.component.MessageInput
import java.util.*

@Composable
fun MessagePage(
    viewModel: MessageViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        backgroundColor = MaterialTheme.colorScheme.background,
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Bottom
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(vertical = 32.dp, horizontal = 16.dp)
            ) {
                items(uiState.messageList) {
                    MessageBubble(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        message = it,
                        lastModified = Date()
                    )
                }
            }

            MessageInput(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(bottom = 8.dp),
                message = uiState.message,
                onMessageChange = {
                    viewModel.onUpdateMessage(it)
                },
                onSaveClick = {
                    viewModel.onSaveMessage()
                }
            )
        }
    }

}