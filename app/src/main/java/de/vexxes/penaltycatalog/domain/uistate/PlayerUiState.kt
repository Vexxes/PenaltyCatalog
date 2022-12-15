package de.vexxes.penaltycatalog.domain.uistate

data class PlayerUiState(
    val id: String = "",
    val number: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val street: String = "",
    val zipcode: String = "",
    val city: String = "",
    val playedGames: String = "",
    val goals: String = "",
    val yellowCards: String = "",
    val twoMinutes: String = "",
    val redCards: String = "",
    val numberError: Boolean = false,
    val firstNameError: Boolean = false,
    val lastNameError: Boolean = false
)