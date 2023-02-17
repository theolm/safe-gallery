package com.theolm.safeGallery.presentation.ui.page.preview

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.theolm.safeGallery.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination(navArgsDelegate = PreviewPageNavArgs::class)
@Composable
fun PreviewPage(
    viewModel: PreviewPageViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState

    Scaffold(
        containerColor = Color.Black,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.savePhoto()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = stringResource(id = R.string.save_photo),
                    )
                },
                text = {
                    Text(stringResource(id = R.string.save_photo))
                },
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                painter = rememberAsyncImagePainter(uiState.tempUri),
                contentDescription = stringResource(id = R.string.photo),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

data class PreviewPageNavArgs(val tempImageUri: Uri)