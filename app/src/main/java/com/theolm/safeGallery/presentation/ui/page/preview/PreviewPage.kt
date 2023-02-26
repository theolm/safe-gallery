@file:OptIn(ExperimentalMaterial3Api::class)

package com.theolm.safeGallery.presentation.ui.page.preview

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.ImageZoom
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination(navArgsDelegate = PreviewPageNavArgs::class)
@Composable
fun PreviewPage(
    navigator: DestinationsNavigator,
    viewModel: PreviewPageViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState
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
}

@Composable
private fun PreviewTopBar(viewModel: PreviewPageViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
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
                        onClick = {
                            scope.launch {
                                val res = viewModel.savePhoto()
                                if (res) {
                                    onBack.invoke()
                                } else {
                                    Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.save_photo))
                    }
                }
            }
        )
    }
}

data class PreviewPageNavArgs(
    val pageType: PreviewPageType,
)
