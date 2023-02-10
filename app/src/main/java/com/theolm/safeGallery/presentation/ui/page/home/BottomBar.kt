package com.theolm.safeGallery.presentation.ui.page.home

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.primarySurface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.page.NavGraphs
import com.theolm.safeGallery.presentation.ui.page.appCurrentDestinationAsState
import com.theolm.safeGallery.presentation.ui.page.destinations.Destination
import com.theolm.safeGallery.presentation.ui.page.destinations.GalleryPageDestination
import com.theolm.safeGallery.presentation.ui.page.destinations.MessagePageDestination
import com.theolm.safeGallery.presentation.ui.page.startAppDestination

@Composable
fun BottomBar(
    currentDestination: Destination,
    onItemClick : (DirectionDestinationSpec) -> Unit,
) {
    BottomNavigation(
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
    Messages(MessagePageDestination, Icons.Outlined.Message, R.string.messages)
}