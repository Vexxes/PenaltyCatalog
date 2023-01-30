package de.vexxes.penaltycatalog.domain.uistate

import de.vexxes.penaltycatalog.domain.model.playerExample
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class PenaltyReceivedUiState(
    val id: String = "",
    val playerId: String = "",
    val playerName: String = "",
    val penaltyId: String = "",
    val penaltyName: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
    val timeOfPenaltyPaid: LocalDate? = null,
    val playerIdError: Boolean = false,
    val penaltyIdError: Boolean = false
)

fun penaltyReceivedUiStateExample1(): PenaltyReceivedUiState {
    val player = playerExample()

    return PenaltyReceivedUiState(
        id = "63cf275e02f099435372c917",
        playerId = "63bb18d15ccb9f5f5e2e70a4",
        playerName = "${player.lastName}, ${player.firstName}",
        penaltyId = "63bf47bf8645030794d0802b",
        penaltyName = "Monatsbeitrag",
        penaltyValue = "5",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.parse("2023-01-20")
    )
}

fun penaltyReceivedUiStateExample2(): PenaltyReceivedUiState {
    val player = playerExample()

    return PenaltyReceivedUiState(
        id = "63d0543ccaabe011e7b18a93",
        playerId = "63bb18d15ccb9f5f5e2e70a4",
        playerName = "${player.lastName}, ${player.firstName}",
        penaltyId = "63bf47bf8645030794d0802b",
        penaltyName = "Monatsbeitrag",
        penaltyValue = "5",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.parse("2023-01-20")
    )
}

fun penaltyReceivedUiStateExample3(): PenaltyReceivedUiState {
    val player = playerExample()

    return PenaltyReceivedUiState(
        id = "63d69c7b4318de2d9b8e259f",
        playerId = "63bb18d15ccb9f5f5e2e70a4",
        playerName = "${player.lastName}, ${player.firstName}",
        penaltyId = "63bf47bf8645030794d0802b",
        penaltyName = "Monatsbeitrag",
        penaltyValue = "1",
        penaltyIsBeer = true,
        timeOfPenalty = LocalDate.parse("2023-01-29"),
        timeOfPenaltyPaid = LocalDate.parse("2023-01-29")
    )
}