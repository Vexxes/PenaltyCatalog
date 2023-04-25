package de.vexxes.penaltycatalog.domain.uistate

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class CancellationUiState(
    val id: String = "",
    val playerId: String = "",
    val playerName: String = "",
    val eventId: String = "",
    val eventTitle: String = "",
    val eventStartOfEvent: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val timeOfCancellation: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val playerIdError: Boolean = false,
    val eventIdError: Boolean = false
)

fun cancellationUiStateExample1(): CancellationUiState {
    return CancellationUiState(
        id = "1",
        playerId = "63bb18d15ccb9f5f5e2e70a4",
        playerName = "Schneider, Thomas",
        eventId = "6402376aed9b5049c1b36959",
        eventTitle = "Regionsliga M Nord TuS FRISIA Goldenstedt vs SFN Vechta III (902068)",
        eventStartOfEvent = LocalDateTime.parse("2023-03-12T16:30"),
        timeOfCancellation = LocalDateTime.parse("2023-03-12T16:30")
    )
}

fun cancellationUiStateExample2(): CancellationUiState {
    return CancellationUiState(
        id = "2",
        playerId = "63bb18d15ccb9f5f5e2e70a4",
        playerName = "Schneider, Thomas",
        eventId = "6402376aed9b5049c1b36959",
        eventTitle = "Training",
        eventStartOfEvent = LocalDateTime.parse("2023-03-12T16:30"),
        timeOfCancellation = LocalDateTime.parse("2023-03-12T16:30")
    )
}

fun cancellationUiStateExample3(): CancellationUiState {
    return CancellationUiState(
        id = "3",
        playerId = "63bb18d15ccb9f5f5e2e70a4",
        playerName = "Schneider, Thomas",
        eventId = "640337f52df65d549ae56804",
        eventTitle = "Training",
        eventStartOfEvent = LocalDateTime.parse("2023-03-12T16:30"),
        timeOfCancellation = LocalDateTime.parse("2023-03-12T16:30")
    )
}