package de.vexxes.penaltycatalog.domain.uievent

import java.time.LocalDate

sealed class PenaltyHistoryUiEvent {
    data class PlayerIdChanged(val playerId: String): PenaltyHistoryUiEvent()
    data class PenaltyIdChanged(val penaltyId: String): PenaltyHistoryUiEvent()
    data class TimeOfPenaltyChanged(val timeOfPenalty: LocalDate): PenaltyHistoryUiEvent()
    data class PenaltyPaidChanged(val penaltyPaid: LocalDate): PenaltyHistoryUiEvent()
}