package de.vexxes.penaltycatalog.domain.uistate

data class PenaltyUiState(
    val id: String = "",
    val name: String = "",
    val categoryName: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: String = "",
    val nameError: Boolean = false,
    val valueError: Boolean = false
)