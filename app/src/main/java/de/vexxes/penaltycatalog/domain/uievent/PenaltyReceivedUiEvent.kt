package de.vexxes.penaltycatalog.domain.uievent

import kotlinx.datetime.LocalDate

sealed class PenaltyReceivedUiEvent {
    data class PlayerIdChanged(val playerId: String): PenaltyReceivedUiEvent()
    data class PenaltyIdChanged(val penaltyId: String): PenaltyReceivedUiEvent()
    data class TimeOfPenaltyChanged(val timeOfPenalty: LocalDate): PenaltyReceivedUiEvent()
    data class PenaltyPaidChanged(val timeOfPenaltyPaid: LocalDate?): PenaltyReceivedUiEvent()
}