package de.vexxes.penaltycatalog.domain.uievent

import java.time.LocalDate

sealed class PenaltyHistoryUiEvent {
    data class PlayerNameChanged(val playerName: String): PenaltyHistoryUiEvent()
    data class PenaltyNameChanged(val id: String, val penaltyName: String): PenaltyHistoryUiEvent()
    data class PenaltyValueChanged(val penaltyValue: String): PenaltyHistoryUiEvent()
    data class PenaltyIsBeerChanged(val penaltyIsBeer: Boolean): PenaltyHistoryUiEvent()
    data class TimeOfPenaltyChanged(val timeOfPenalty: LocalDate): PenaltyHistoryUiEvent()
    data class PenaltyPaidChanged(val penaltyPaid: Boolean): PenaltyHistoryUiEvent()
}