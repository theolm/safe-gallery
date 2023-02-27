package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeDark
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeLight

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewLight() {
    PreviewThemeLight {
        CustomTextField(
            value = "",
            onValueChange = {},
            placeholder = "Message title"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewDark() {
    PreviewThemeDark {
        CustomTextField(
            value = "",
            onValueChange = {},
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    placeholder: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
) {
    var hasFocus by remember { mutableStateOf(false) }

    BasicTextField(
        modifier = modifier.onFocusEvent {
            hasFocus = it.isFocused
        },
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = singleLine,
        readOnly = readOnly,
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        maxLines = maxLines,
        minLines = minLines,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            if (value.isEmpty() && placeholder != null && (!hasFocus && !enabled)) {
                Text(
                    text = placeholder,
                    style = textStyle.copy(
                        color = textStyle.color.copy(alpha = 0.6f)
                    )
                )
            } else {
                innerTextField()
            }
        },
    )
}
