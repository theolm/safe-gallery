package com.theolm.safeGallery.presentation.ui.page.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.theolm.safeGallery.presentation.ui.page.messages.MessagePage
import com.theolm.safeGallery.presentation.ui.page.messages.NavGraphs

@Composable
fun HomePage() {
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}

