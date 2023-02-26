package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeDark
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeLight

@Preview(showBackground = true)
@Composable
private fun PreviewLight() {
    PreviewThemeLight {
        ConfirmationAlertDialog(
            showAlert = true,
            title = "Title test",
            message = "Message testing the component",
            confirmButton = "Delete",
            cancelButton = "Cancel",
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDark() {
    PreviewThemeDark {
        ConfirmationAlertDialog(
            showAlert = true,
            title = "Title test",
            message = "Message testing the component",
            confirmButton = "Delete",
            cancelButton = "Cancel",
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Composable
fun ConfirmationAlertDialog(
    showAlert: Boolean,
    title: String,
    message: String,
    confirmButton: String,
    confirmButtonColor: Color = MaterialTheme.colorScheme.primary,
    cancelButton: String,
    cancelButtonColor: Color = MaterialTheme.colorScheme.primary,
    onDismiss: () -> Unit,
    onConfirm: ()-> Unit,
) {
    AnimatedVisibility(
        visible = showAlert,
        enter = fadeIn(),
        exit = ExitTransition.None,
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(
                        text = confirmButton,
                        color = confirmButtonColor
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = cancelButton,
                        color = cancelButtonColor
                    )
                }
            }
        )
    }
}
