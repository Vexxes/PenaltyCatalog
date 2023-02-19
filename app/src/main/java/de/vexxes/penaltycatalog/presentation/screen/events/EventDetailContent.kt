package de.vexxes.penaltycatalog.presentation.screen.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.R
import de.vexxes.penaltycatalog.domain.enums.EventType
import de.vexxes.penaltycatalog.domain.uistate.EventUiState
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample1
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample2
import de.vexxes.penaltycatalog.domain.uistate.eventUiStateExample3
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun EventDetailContent(
    eventUiState: EventUiState,
    onMapsClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        EventHeader(text = eventUiState.title)
        EventType(type = eventUiState.type)
        EventStartOfEvent(dateTime = eventUiState.startOfEvent)
        EventStartOfMeeting(dateTime = eventUiState.startOfMeeting)
        EventAddress(address = eventUiState.address, onMapsClicked = onMapsClicked)
        if (eventUiState.description.isNotEmpty()) EventDescription(description = eventUiState.description)
    }
}

@Composable
private fun EventHeader(
    text: String
) {
    Text(
        text = text,
        style = Typography.headlineMedium
    )
}

@Composable
private fun EventType(
    type: EventType
) {
    LabelHeader(text = stringResource(id = R.string.Type))
    TextValue(text = stringResource(id = type.nameId))
}

@Composable
private fun EventStartOfEvent(
    dateTime: LocalDateTime
) {
    val output = DateTimeFormatter
        .ofPattern("eeee, dd. MMMM y HH:mm:ss")
        .format(dateTime.toJavaLocalDateTime())

    LabelHeader(text = stringResource(id = R.string.StartOfEvent))
    TextValue(text = output)
}

@Composable
private fun EventStartOfMeeting(
    dateTime: LocalDateTime
) {
    val output = DateTimeFormatter
        .ofPattern("HH:mm")
        .format(dateTime.toJavaLocalDateTime())

    LabelHeader(text = stringResource(id = R.string.StartOfMeeting))
    TextValue(text = output)
}

@Composable
private fun EventAddress(
    address: String,
    onMapsClicked: () -> Unit
) {
    LabelHeader(text = stringResource(id = R.string.Address))

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextValue(text = address)

        IconButton(
            onClick = { onMapsClicked() }
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Outlined.Map,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun EventDescription(
    description: String
) {
    LabelHeader(text = stringResource(id = R.string.Description))
    TextValue(text = description)
}
@Composable
private fun LabelHeader(
    text: String
) {
    Text(
        modifier = Modifier
            .padding(top = 32.dp),
        text = text,
        style = Typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun TextValue(
    text: String
) {
    Text(
        modifier = Modifier,
//            .fillMaxWidth(),
        text = text,
        style = Typography.titleLarge
    )
}

@Composable
@Preview(showBackground = true)
private fun EventDetailContentPreview1() {
    EventDetailContent(
        eventUiState = eventUiStateExample1(),
        onMapsClicked = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun EventDetailContentPreview2() {
    EventDetailContent(
        eventUiState = eventUiStateExample2(),
        onMapsClicked = { }
    )
}

@Composable
@Preview(showBackground = true)
private fun EventDetailContentPreview3() {
    EventDetailContent(
        eventUiState = eventUiStateExample3(),
        onMapsClicked = { }
    )
}