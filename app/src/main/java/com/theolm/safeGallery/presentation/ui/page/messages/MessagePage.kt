package com.theolm.safeGallery.presentation.ui.page.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theolm.safeGallery.presentation.ui.component.MessageInput

@Composable
fun MessagePage(
    viewModel: MessageViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    Scaffold(
        modifier = Modifier.fillMaxSize().imePadding()
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
                    .weight(1f)
                    .background(Color.Blue)
            ) {
                items(uiState.messageList) {

                }
            }

            MessageInput(
                modifier = Modifier.padding(16.dp).padding(bottom = 8.dp),
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