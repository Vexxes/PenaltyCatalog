package de.vexxes.penaltycatalog.presentation.screen.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.vexxes.penaltycatalog.component.EmptyContent
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.eventExample1
import de.vexxes.penaltycatalog.domain.model.eventExample2
import de.vexxes.penaltycatalog.domain.model.eventExample3
import de.vexxes.penaltycatalog.ui.theme.PenaltyCatalogTheme
import de.vexxes.penaltycatalog.ui.theme.Typography
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun EventListContent(
    events: List<Event>,
    navigateToEventDetailScreen: (eventId: String) -> Unit
) {
    if (events.isNotEmpty()) {
        DisplayEvents(
            events = events,
            navigateToEventDetailScreen = navigateToEventDetailScreen
        )
    } else {
        EmptyContent()
    }
}

@Composable
private fun DisplayEvents(
    events: List<Event>,
    navigateToEventDetailScreen: (eventId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = events,
            key = { event ->
                event.hashCode()
            }
        ) { event ->
            EventItem(
                event = event,
                navigateToEventDetailScreen = navigateToEventDetailScreen
            )
        }
    }
}

@Composable
private fun EventItem(
    event: Event,
    navigateToEventDetailScreen: (eventId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { navigateToEventDetailScreen(event.id) }
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .height(88.dp),
        verticalArrangement = Arrangement.Center
    ) {
        EventTitle(text = event.title)
        EventStart(dateTime = event.startOfEvent)
        EventSubText(text = event.address)
    }
}

@Composable
private fun EventTitle(
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        style = Typography.titleSmall,
        textAlign = TextAlign.Left
    )
}

@Composable
private fun EventStart(
    dateTime: LocalDateTime
) {
    val output = DateTimeFormatter
        .ofPattern("eeee, dd. MMMM y HH:mm:ss")
        .format(dateTime.toJavaLocalDateTime())

    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = output,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = Typography.bodyMedium,
        textAlign = TextAlign.Left
    )
}

@Composable
private fun EventSubText(
    text: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = Typography.bodyMedium,
        textAlign = TextAlign.Left
    )
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
private fun EventItemPreview() {
    PenaltyCatalogTheme {
        EventItem(
            event = eventExample1(),
            navigateToEventDetailScreen = { }
        )
    }
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
private fun EventListContentPreview() {
    PenaltyCatalogTheme {
        EventListContent(
            events = listOf(
                eventExample1(),
                eventExample2(),
                eventExample3()
            ),
            navigateToEventDetailScreen = { }
        )
    }
}