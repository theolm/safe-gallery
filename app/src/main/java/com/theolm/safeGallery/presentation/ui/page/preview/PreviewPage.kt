@file:OptIn(ExperimentalMaterial3Api::class)

package com.theolm.safeGallery.presentation.ui.page.preview

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.ConfirmationAlertDialog
import com.theolm.safeGallery.presentation.ui.components.ImageZoom

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination(navArgsDelegate = PreviewPageNavArgs::class)
@Composable
fun PreviewPage(
    navigator: DestinationsNavigator,
    viewModel: PreviewPageViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState

    DeleteAlert(viewModel = viewModel, showAlert = uiState.showDeleteAlert)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            PreviewTopBar(
                viewModel = viewModel,
                onBack = {
                    navigator.popBackStack()
                }
            )
        },
    ) {
        ImageZoom(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(uiState.uri),
            contentDescription = stringResource(id = R.string.photo),
            onClick = viewModel::onImageClicked
        )
    }

    //Observe close events
    if (viewModel.closePage) {
        navigator.popBackStack()
    }
}

@Composable
private fun PreviewTopBar(viewModel: PreviewPageViewModel, onBack: () -> Unit) {
    val uiState = viewModel.uiState

    val containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
    val contentColor = MaterialTheme.colorScheme.onBackground

    AnimatedVisibility(
        visible = uiState.visibleTools,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor,
                navigationIconContentColor = contentColor,
                titleContentColor = contentColor,
                actionIconContentColor = contentColor
            ),
            navigationIcon = {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            },
            actions = {
                if (uiState.type is PreviewPageType.NewPhoto) {
                    TextButton(
                        colors = ButtonDefaults.buttonColors(
                            contentColor = contentColor,
                            containerColor = Color.Transparent
                        ),
                        onClick = viewModel::savePhoto
                    ) {
                        Text(text = stringResource(id = R.string.save_photo))
                    }
                }

                if (uiState.type is PreviewPageType.Photo) {
                    IconButton(onClick = viewModel::onDeleteClick) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(id = R.string.delete_photo)
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun DeleteAlert(viewModel: PreviewPageViewModel, showAlert: Boolean) {
    ConfirmationAlertDialog(
        showAlert = showAlert,
        title = stringResource(id = R.string.delete_photo),
        message = stringResource(id = R.string.delete_photo_message),
        confirmButton = stringResource(id = R.string.delete),
        confirmButtonColor = MaterialTheme.colorScheme.error,
        cancelButton = stringResource(id = R.string.cancel),
        onDismiss = viewModel::onCloseDeleteAlert,
        onConfirm = viewModel::onDeleteConfirm
    )
}

data class PreviewPageNavArgs(
    val pageType: PreviewPageType,
)
