package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: Int,
    val number: Int,
    val firstName: String,
    val lastName: String,
    val zipcode: Int,
    val city: String,
    val playedGames: Int,
    val goals: Int,
    val yellowCards: Int,
    val twoMinutes: Int,
    val redCards: Int
)