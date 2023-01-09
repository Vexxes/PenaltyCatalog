package de.vexxes.penaltycatalog.domain.uievent

sealed class PenaltyTypeUiEvent {
    data class NameChanged(val name: String): PenaltyTypeUiEvent()
    data class DescriptionChanged(val description: String): PenaltyTypeUiEvent()
    data class IsBeerChanged(val isBeer: Boolean): PenaltyTypeUiEvent()
    data class ValueChanged(val value: String): PenaltyTypeUiEvent()
}