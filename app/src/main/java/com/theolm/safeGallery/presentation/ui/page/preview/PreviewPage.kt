package com.theolm.safeGallery.presentation.ui.page.preview

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.safeGallery.BuildConfig
import java.io.File

@Destination
@Composable
fun PreviewPage(
    navigator: DestinationsNavigator,
    viewModel: PreviewPageViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.loadImage(uiState.tempUri)
        } else {
            navigator.popBackStack()
        }
    }

    LaunchedEffect(true) { launcher.launch(uiState.tempUri) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        uiState.uri?.let {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                painter = rememberImagePainter(data = it),
                contentDescription = "Image",
                contentScale = ContentScale.FillWidth
            )
        }
    }
}
