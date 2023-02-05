package com.theolm.safeGallery.presentation.ui.page.messages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theolm.safeGallery.presentation.ui.component.MessageBubble
import com.theolm.safeGallery.presentation.ui.component.MessageInput
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MessagePage(
    viewModel: MessageViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val messageList by viewModel.messageFlow.collectAsState(initial = listOf())
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        backgroundColor = MaterialTheme.colorScheme.background,
    ) {
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
                items(messageList) {
                    MessageBubble(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        message = it.message,
                        lastModified = Date(it.updatedAt)
                    )
                }
            }

            MessageInput(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(bottom = 8.dp),
                message = uiState.inputMessage,
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