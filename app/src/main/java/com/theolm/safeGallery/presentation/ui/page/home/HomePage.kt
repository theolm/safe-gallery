package com.theolm.safeGallery.presentation.ui.page.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.core.data.LockState

@RootNavGraph(start = true)
@Destination
@Composable
fun HomePage(
    navigator: DestinationsNavigator,
    viewModel: HomePageViewModel = hiltViewModel(),
) {
    val state by viewModel.lockState.collectAsState(initial = LockState.LOCK)
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state == LockState.UNLOCK) {
            UnlockedScreen()
        } else {
            LockedScreen {
                viewModel.askBiometric(context)
            }
        }
    }
}

@Composable
fun LockedScreen(onClick: () -> Unit) {
    Column {
        Text(text = "Locked")
        Button(onClick = onClick) {
            Text(text = "Unlock with fingerprint")
        }
    }
}

@Composable
fun UnlockedScreen() {
    Column {
        Text(text = "Unlocked!!!")
    }
}
