package de.vexxes.penaltycatalog.domain.uistate

data class PenaltyUiState(
    val id: String = "",
    val name: String = "",
    val categoryName: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: String = "",
    val valueDeclaredPenalties: String = "0",
    val nameError: Boolean = false,
    val valueError: Boolean = false
)

fun penaltyUiStateExample1(): PenaltyUiState {
    return PenaltyUiState(
        id = "63717e8314ab74703f0ab5cb",
        name = "Monatsbeitrag",
        categoryName = "Monatsbeitrag",
        description = "",
        isBeer = false,
        value = "500",
        valueDeclaredPenalties = "10"
    )
}

fun penaltyUiStateExample2(): PenaltyUiState {
    return PenaltyUiState(
        id = "63717e8314ab74703f0ab5cb",
        name = "Getränke zur Besprechung",
        categoryName = "Sonstiges",
        description = "",
        isBeer = true,
        value = "1",
        valueDeclaredPenalties = "10"
    )
}