package de.vexxes.penaltycatalog.domain.uievent

sealed class PlayerUiEvent {
//    data class IdChanged(val id: String): PlayerUiEvent()
    data class NumberChanged(val number: String): PlayerUiEvent()
    data class FirstNameChanged(val firstName: String): PlayerUiEvent()
    data class LastNameChanged(val lastName: String): PlayerUiEvent()
    data class BirthdayChanged(val birthday: String): PlayerUiEvent()
    data class StreetChanged(val street: String): PlayerUiEvent()
    data class ZipcodeChanged(val zipcode: String): PlayerUiEvent()
    data class CityChanged(val city: String): PlayerUiEvent()
    data class PlayedGamesChanged(val playedGames: String): PlayerUiEvent()
    data class GoalsChanged(val goals: String): PlayerUiEvent()
    data class YellowCardsChanged(val yellowCards: String): PlayerUiEvent()
    data class TwoMinutesChanged(val twoMinutes: String): PlayerUiEvent()
    data class RedCardsChanged(val redCards: String): PlayerUiEvent()
}