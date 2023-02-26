package com.theolm.safeGallery.presentation.ui.page.gallery

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.theolm.core.data.SafePhoto
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.BottomNavigationHeight
import com.theolm.safeGallery.presentation.ui.page.destinations.PreviewPageDestination
import com.theolm.safeGallery.presentation.ui.page.preview.PreviewPageNavArgs
import com.theolm.safeGallery.presentation.ui.page.preview.PreviewPageType
import kotlinx.coroutines.launch

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

    val launcher = photoIntentLauncher(navigator, viewModel)
    val scope = rememberCoroutineScope()
    val listState = rememberLazyGridState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val photos by viewModel.photos.collectAsState(initial = listOf())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.gallery)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                viewModel.bypassLock()
                                viewModel.refreshTempFile()
                                launcher.launch(viewModel.tempUri)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Camera,
                            contentDescription = stringResource(id = R.string.take_photo)
                        )
                    }
                }
            )
        },
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
            items(photos) { safePhoto ->
                GalleryTile(photo = safePhoto) {
                    navigator.navigate(
                        PreviewPageDestination(
                            navArgs = PreviewPageNavArgs(
                                pageType = PreviewPageType.Photo(safePhoto)
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun GalleryTile(
    photo: SafePhoto,
    onClick: () -> Unit,
) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        painter = rememberAsyncImagePainter(photo.uri),
        contentDescription = stringResource(id = R.string.photo),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun photoIntentLauncher(
    navigator: DestinationsNavigator,
    viewModel: GalleryViewModel
) = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
    if (it) {
        viewModel.tempUri?.let { newUri ->
            navigator.navigate(
                PreviewPageDestination(
                    navArgs = PreviewPageNavArgs(
                        pageType = PreviewPageType.NewPhoto(newUri)
                    )
                )
            )
        }
    } else {
        navigator.popBackStack()
    }
}