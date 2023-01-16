package de.vexxes.penaltycatalog.domain.uistate

import java.time.LocalDate

data class PenaltyHistoryUiState(
    val id: String = "",
    val playerId: String = "",
    val penaltyId: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: LocalDate = LocalDate.now(),
    val penaltyPaid: LocalDate = LocalDate.MIN, // TODO: Other start date?,
    val playerIdError: Boolean = false,
    val penaltyIdError: Boolean = false
)

fun penaltyHistoryUiStateExample1(): PenaltyHistoryUiState {
    return PenaltyHistoryUiState(
        id = "",
        playerId = "",
        penaltyId = "",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now()
    )
}

fun penaltyHistoryUiStateExample2(): PenaltyHistoryUiState {
    return PenaltyHistoryUiState(
        id = "",
        playerId = "",
        penaltyId = "",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now(),
    )
}

fun penaltyHistoryUiStateExample3(): PenaltyHistoryUiState {
    return PenaltyHistoryUiState(
        id = "",
        playerId = "",
        penaltyId = "",
        penaltyValue = "1",
        penaltyIsBeer = true,
        timeOfPenalty = LocalDate.now()
    )
}