package com.theolm.safeGallery.presentation.ui.page.gallery

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.BottomNavigationHeight
import com.theolm.safeGallery.presentation.ui.page.destinations.PreviewPageDestination
import com.theolm.safeGallery.presentation.ui.page.preview.PreviewPageNavArgs

private const val spaceBetweenCells = 4

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun GalleryPage(
    navigator: DestinationsNavigator,
    viewModel: GalleryViewModel = hiltViewModel(),
) {

    val listState = rememberLazyGridState()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.tempUri?.let {
                navigator.navigate(PreviewPageDestination(navArgs = PreviewPageNavArgs(it)))
            }
        } else {
            navigator.popBackStack()
        }
    }

    val photos by viewModel.photos.collectAsState(initial = listOf())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.refreshTempFile()
                    launcher.launch(viewModel.tempUri)
                }
            ) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            state = listState,
            contentPadding = PaddingValues(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = 0.dp,
                end = 0.dp
            ),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(spaceBetweenCells.dp),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenCells.dp)
        ) {
            items(photos) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    painter = rememberAsyncImagePainter(it.uri),
                    contentDescription = stringResource(id = R.string.photo),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
