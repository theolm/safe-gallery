package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val NavigationBarItemHorizontalPadding: Dp = 8.dp
val BottomNavigationHeight = 56.dp

/**
 * This is the same component present in the androidx.compose.material
 * the only difference is the height of the bottom bar is public and can be changed with @param height
 */
@Composable
fun SafeBottomNavigation(
    modifier: Modifier = Modifier,
    height: Dp = BottomNavigationHeight,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {

    Surface(
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
                .height(height)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(NavigationBarItemHorizontalPadding),
            content = content
        )
    }
}