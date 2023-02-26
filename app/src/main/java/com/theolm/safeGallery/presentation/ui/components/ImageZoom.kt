package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun ImageZoom(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String?,
    onClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    val screenHeight = configuration.screenHeightDp.dp.value

    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .clip(RectangleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f..3f)

                    if (scale > 1) {
                        val x = (pan.x * scale)
                        val y = (pan.y * scale)

                        offsetX =
                            (offsetX + x).coerceIn(-(screenWidth * zoom)..(screenWidth * zoom))
                        offsetY =
                            (offsetY + y).coerceIn(-(screenHeight * zoom)..(screenHeight * zoom))
                    } else {
                        offsetX = 0f
                        offsetY = 0f
                    }

                    Timber.i("pan: $pan zoom: $zoom - image scale: $scale - offsetX: $offsetX offsetY: $offsetY")
                }
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                ),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth
        )
    }
}
