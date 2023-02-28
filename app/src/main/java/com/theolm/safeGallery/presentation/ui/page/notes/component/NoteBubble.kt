package com.theolm.safeGallery.presentation.ui.page.notes.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theolm.safeGallery.R
import com.theolm.safeGallery.extensions.toDateAndTime
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeDark
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeLight
import java.util.*


private const val mockNote =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Nullam tincidunt dui dignissim velit iaculis pulvinar. Donec eget lacus volutpat, " +
            "efficitur libero vel, ornare ex. Cras aliquet ex vitae faucibus fermentum. " +
            "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus."


private const val maxLines = 10000
private const val minLines = 4
private const val titleMaxLines = 10000
private const val titleMinLines = 2

@Preview
@Composable
private fun PreviewLight() {
    PreviewThemeLight {
        NoteBubble(
            title = mockNote,
            note = mockNote,
            lastModified = Date(),
        )
    }
}

@Preview
@Composable
private fun PreviewDark() {
    PreviewThemeDark {
        NoteBubble(
            title = mockNote,
            note = mockNote,
            lastModified = Date(),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteBubble(
    modifier: Modifier = Modifier,
    title: String,
    note: String,
    lastModified: Date,
    onClick: () -> Unit = {},
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val elevation by animateDpAsState(targetValue = if (isExpanded) 4.dp else 0.dp)
    val maxLines by animateIntAsState(targetValue = if (isExpanded) maxLines else minLines)
    val titleMaxLines by animateIntAsState(targetValue = if (isExpanded) titleMaxLines else titleMinLines)
    val verticalPadding by animateDpAsState(targetValue = if (isExpanded) 8.dp else 4.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = verticalPadding,
                bottom = verticalPadding
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
    ) {
        Box(
            modifier = Modifier.combinedClickable(
                onClick = onClick,
                onLongClick = {
                    isExpanded = !isExpanded
                },
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = titleMaxLines,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = note,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = "${stringResource(id = R.string.last_modified)} ${lastModified.toDateAndTime()}",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                )
            }
        }

    }
}