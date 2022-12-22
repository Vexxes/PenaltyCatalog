package de.vexxes.penaltycatalog.domain.uistate

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class PenaltyHistoryUiState(
    val id: String = "",
    val playerName: String = "",
    val penaltyName: String = "",
    val penaltyValue: String = "",
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: Instant = Clock.System.now(),
    val penaltyPaid: Boolean = false
)