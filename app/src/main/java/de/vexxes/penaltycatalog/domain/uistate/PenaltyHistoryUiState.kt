package de.vexxes.penaltycatalog.domain.uistate

import java.time.LocalDate

data class PenaltyHistoryUiState(
    val id: String = "",
    val playerName: String = "",
    val penaltyName: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: LocalDate = LocalDate.now(),
    val penaltyPaid: Boolean = false
)

fun penaltyHistoryUiStateExample1(): PenaltyHistoryUiState {
    return PenaltyHistoryUiState(
        id = "",
        playerName = "Mustermann, Max",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = false
    )
}

fun penaltyHistoryUiStateExample2(): PenaltyHistoryUiState {
    return PenaltyHistoryUiState(
        id = "",
        playerName = "Mustermann, Max",
        penaltyName = "Verspätete Zahlung des Monatsbeitrag",
        penaltyValue = "500",
        penaltyIsBeer = false,
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = true
    )
}

fun penaltyHistoryUiStateExample3(): PenaltyHistoryUiState {
    return PenaltyHistoryUiState(
        id = "",
        playerName = "Mustermann, Max",
        penaltyName = "Geburtstag",
        penaltyValue = "1",
        penaltyIsBeer = true,
        timeOfPenalty = LocalDate.now(),
        penaltyPaid = true
    )
}