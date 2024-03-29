package de.vexxes.penaltycatalog.domain.uistate

import de.vexxes.penaltycatalog.domain.enums.EventType
import de.vexxes.penaltycatalog.domain.model.EventPlayerAvailability
import de.vexxes.penaltycatalog.domain.model.Player
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class EventUiState(
    val id: String = "",
    val title: String = "",
    val startOfEvent: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val startOfMeeting: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val address: String = "",
    val description: String = "",
    val eventAvailablePlayers: List<Player> = emptyList(),
    val playerAvailability: List<EventPlayerAvailability> = emptyList(),
    val type: EventType = EventType.TRAINING,
    val titleError: Boolean = false
)

fun eventUiStateExample1(): EventUiState {
    return EventUiState(
        title = "Regionsliga M Nord TV Cloppenburg III vs TuS FRISIA Goldenstedt (902056)",
        startOfEvent = LocalDateTime.parse("2023-03-12T16:30"),
        startOfMeeting = LocalDateTime.parse("2023-03-12T15:30"),
        address = "Schulstraße, 49661 Cloppenburg",
        type = EventType.GAME
    )
}

fun eventUiStateExample2(): EventUiState {
    return EventUiState(
        title = "Regionsliga M Nord BV Garrel vs TuS FRISIA Goldenstedt (902061)",
        startOfEvent = LocalDateTime.parse("2023-04-22T17:00"),
        startOfMeeting = LocalDateTime.parse("2023-04-22T16:00"),
        address = "St.-Johannes-Straße, 49681 Garrel",
        description =
        "Regionsliga M Nord\n" +
                "Begegnungs-Nr: 902061\n" +
                "Heim: BV Garrel\n" +
                "Gast: TuS FRISIA Goldenstedt\n" +
                "Halle: Garrel, SZ (809115)\n" +
                "Adresse: St.-Johannes-Straße, 49681 Garrel",
        type = EventType.GAME
    )
}

fun eventUiStateExample3(): EventUiState {
    return EventUiState(
        title = "Training",
        startOfEvent = LocalDateTime.parse("2023-04-28T20:30"),
        startOfMeeting = LocalDateTime.parse("2023-04-28T20:15"),
        address = "An der Marienschule, 49424 Goldenstedt",
        type = EventType.TRAINING
    )
}