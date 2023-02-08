package com.theolm.safeGallery.presentation.ui.page.messages.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    message: String,
    onMessageChange: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                ),
            )

            IconButton(
                onClick = {
                    keyboardController?.hide()
                    onSaveClick.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Save,
                    contentDescription = stringResource(id = R.string.save_message),
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
