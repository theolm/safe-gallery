package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeDark
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeLight

@Preview(showBackground = true)
@Composable
private fun PreviewLight() {
    PreviewThemeLight {
        BasicAlertDialog({}) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDark() {
    PreviewThemeDark {
        BasicAlertDialog({}) {}
    }
}

/**
 * The implementation of this component is following the Material3 guidelines of the Basic dialogs
 * https://m3.material.io/components/dialogs/specs
 */
@Composable
fun BasicAlertDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .widthIn(min = 280.dp, max = 560.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 6.dp,
        ) {
            Box(modifier = Modifier.padding(24.dp)) {
                content.invoke()
            }
        }
    }
}