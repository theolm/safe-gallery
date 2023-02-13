package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBar(
    title: String,
    onBackPress: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    actions: @Composable RowScope.() -> Unit = {},
) {
    //TODO: fix topbar animation
    LargeTopAppBar(
        title = {
            Text(text = title)
        },
        colors = colors,
        navigationIcon = {
            onBackPress?.let {
                IconButton(onClick = it) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions,
    )
}

@Composable
fun ToolBarAction(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}