package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val _id: String = "",
    val number: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val street: String = "",
    val zipcode: Int = 0,
    val city: String = "",
    val playedGames: Int = 0,
    val goals: Int = 0,
    val yellowCards: Int = 0,
    val twoMinutes: Int = 0,
    val redCards: Int = 0
)