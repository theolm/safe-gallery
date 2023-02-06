package com.theolm.safeGallery.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun OptionsAlertDialog(
    vararg options: OptionsAlertItem,
    onDismiss: () -> Unit = {},
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Column {
            options.forEach {
                TextButton(
                    onClick = it.onClick,
                    colors = ButtonDefaults.textButtonColors(contentColor = it.color)
                ) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(text = it.text,)
                }
            }
        }
    }
}

data class OptionsAlertItem(
    val text: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)