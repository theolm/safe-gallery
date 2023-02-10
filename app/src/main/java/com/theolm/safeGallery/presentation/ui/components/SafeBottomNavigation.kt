package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val BottomNavigationHeight = 56.dp

/**
 * This is the same component present in the androidx.compose.material
 * the only difference is the height of the bottom bar is public and can be changed with @param height
 */
@Composable
fun SafeBottomNavigation(
    modifier: Modifier = Modifier,
    height: Dp = BottomNavigationHeight,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = BottomNavigationDefaults.Elevation,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(height)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}