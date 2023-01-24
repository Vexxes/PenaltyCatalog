package de.vexxes.penaltycatalog.domain.uistate

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class PenaltyReceivedUiState(
    val id: String = "",
    val playerId: String = "",
    val penaltyId: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
    val timeOfPenaltyPaid: LocalDate? = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
    val playerIdError: Boolean = false,
    val penaltyIdError: Boolean = false
)

fun penaltyReceivedUiStateExample1(): PenaltyReceivedUiState {
    return PenaltyReceivedUiState(
        id = "",
        playerId = "",
        penaltyId = "",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    )
}

fun penaltyReceivedUiStateExample2(): PenaltyReceivedUiState {
    return PenaltyReceivedUiState(
        id = "",
        playerId = "",
        penaltyId = "",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    )
}

fun penaltyReceivedUiStateExample3(): PenaltyReceivedUiState {
    return PenaltyReceivedUiState(
        id = "",
        playerId = "",
        penaltyId = "",
        penaltyValue = "1",
        penaltyIsBeer = true,
        timeOfPenalty = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
        timeOfPenaltyPaid = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    )
}