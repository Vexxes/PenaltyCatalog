package de.vexxes.penaltycatalog.domain.uievent

sealed class PenaltyUiEvent {
    data class NameChanged(val name: String): PenaltyUiEvent()
    data class CategoryNameChanged(val categoryName: String): PenaltyUiEvent()
    data class DescriptionChanged(val description: String): PenaltyUiEvent()
    data class IsBeerChanged(val isBeer: Boolean): PenaltyUiEvent()
    data class ValueChanged(val value: String): PenaltyUiEvent()
}