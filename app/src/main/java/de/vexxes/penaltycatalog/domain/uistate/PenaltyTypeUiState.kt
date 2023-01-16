package de.vexxes.penaltycatalog.domain.uistate

data class PenaltyTypeUiState(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: String = "",
    val valueDeclaredPenalties: String = "0",
    val nameError: Boolean = false,
    val valueError: Boolean = false
)

fun penaltyTypeUiStateExample1(): PenaltyTypeUiState {
    return PenaltyTypeUiState(
        id = "63717e8314ab74703f0ab5cb",
        name = "Monatsbeitrag",
        description = "",
        isBeer = false,
        value = "5",
        valueDeclaredPenalties = "10"
    )
}

fun penaltyTypeUiStateExample2(): PenaltyTypeUiState {
    return PenaltyTypeUiState(
        id = "63717e8314ab74703f0ab5cb",
        name = "Getränke zur Besprechung",
        description = "",
        isBeer = true,
        value = "1",
        valueDeclaredPenalties = "10"
    )
}