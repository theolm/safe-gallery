package com.theolm.safeGallery.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeDark
import com.theolm.safeGallery.presentation.ui.theme.PreviewThemeLight
import com.theolm.safeGallery.R
import com.theolm.safeGallery.extensions.toDateAndTime
import java.util.*


private const val mockMessage =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Nullam tincidunt dui dignissim velit iaculis pulvinar. Donec eget lacus volutpat, " +
            "efficitur libero vel, ornare ex. Cras aliquet ex vitae faucibus fermentum. " +
            "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus."

@Preview
@Composable
private fun PreviewLight() {
    PreviewThemeLight {
        MessageBubble(message = mockMessage, lastModified = Date())
    }
}

@Preview
@Composable
private fun PreviewDark() {
    PreviewThemeDark {
        MessageBubble(message = mockMessage, lastModified = Date())
    }
}

@Composable
fun MessageBubble(
    modifier: Modifier = Modifier,
    message: String,
    lastModified: Date
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge
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