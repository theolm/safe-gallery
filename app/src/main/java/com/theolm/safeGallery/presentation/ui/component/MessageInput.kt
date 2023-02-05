package com.theolm.safeGallery.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theolm.safeGallery.R
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeDark
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeLight

private const val mockMessage = "Testing secure message"

@Preview
@Composable
private fun PreviewLight() {
    PreviewThemeLight {
        MessageInput(message = mockMessage)
    }
}

@Preview
@Composable
private fun PreviewDark() {
    PreviewThemeDark {
        MessageInput(message = mockMessage)
    }
}

@Preview
@Composable
private fun PreviewPlaceholderLight() {
    PreviewThemeLight {
        MessageInput(message = "")
    }
}

@Preview
@Composable
private fun PreviewPlaceholderDark() {
    PreviewThemeDark {
        MessageInput(message = "")
    }
}

@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    message: String,
    onMessageChange: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    val shape = RoundedCornerShape(24.dp)
    Card(
        modifier = modifier,
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            BasicTextField(
                modifier = Modifier
                    .defaultMinSize(minHeight = 50.dp)
                    .heightIn(max = 120.dp)
                    .weight(1f)
                    .padding(all = 16.dp),
                value = message,
                onValueChange = onMessageChange,
                singleLine = false,
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                decorationBox = { innerTextField ->
                    if (message.isEmpty()) {
                        Placeholder()
                    } else {
                        innerTextField()
                    }
                }
            )

            IconButton(onClick = onSaveClick) {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = "Save Message",
                )
            }
        }
    }
}

@Composable
private fun Placeholder() {
    Text(
        text = stringResource(id = R.string.placeholder_message_input),
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    )
}
