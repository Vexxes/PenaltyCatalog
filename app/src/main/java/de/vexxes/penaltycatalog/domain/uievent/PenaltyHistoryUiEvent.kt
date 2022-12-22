package de.vexxes.penaltycatalog.domain.uievent

import kotlinx.datetime.Instant

sealed class PenaltyHistoryUiEvent {
    data class PlayerNameChanged(val playerName: String): PenaltyHistoryUiEvent()
    data class PenaltyNameChanged(val penaltyName: String): PenaltyHistoryUiEvent()
    data class PenaltyValueChanged(val penaltyValue: String): PenaltyHistoryUiEvent()
    data class PenaltyIsBeerChanged(val penaltyIsBeer: Boolean): PenaltyHistoryUiEvent()
    data class TimeOfPenaltyChanged(val timeOfPenalty: Instant): PenaltyHistoryUiEvent()
    data class PenaltyPaidChanged(val penaltyPaid: Boolean): PenaltyHistoryUiEvent()
}