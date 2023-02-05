package com.theolm.safeGallery.presentation.ui.page.lock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LockedPage(viewModel: LockPageViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Text(text = "Locked")
            Button(
                onClick = {
                    viewModel.askBiometric(context)
                }
            ) {
                Text(text = "Unlock with fingerprint")
            }
        }
    }
}
