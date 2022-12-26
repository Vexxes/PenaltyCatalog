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