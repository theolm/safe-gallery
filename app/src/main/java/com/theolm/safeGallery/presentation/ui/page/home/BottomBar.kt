package com.theolm.safeGallery.presentation.ui.page.home

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.components.SafeBottomNavigation
import com.theolm.safeGallery.presentation.ui.page.destinations.Destination
import com.theolm.safeGallery.presentation.ui.page.destinations.GalleryPageDestination
import com.theolm.safeGallery.presentation.ui.page.destinations.NotesPageDestination

@Composable
fun BottomBar(
    currentDestination: Destination,
    onItemClick : (DirectionDestinationSpec) -> Unit,
) {
    SafeBottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        elevation = BottomNavigationDefaults.Elevation,
    ) {
        BottomBarDestination.values().forEach { destination ->
            val selected = currentDestination == destination.direction
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    onItemClick.invoke(destination.direction)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.label),
                        tint = if(selected) {
                            MaterialTheme.colorScheme.primary
                        } else  {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                },
            )
        }
    }
}

private enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Gallery(GalleryPageDestination, Icons.Outlined.Photo, R.string.gallery),
    Notes(NotesPageDestination, Icons.Outlined.Message, R.string.note)
}