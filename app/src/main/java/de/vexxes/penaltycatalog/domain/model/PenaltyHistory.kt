package de.vexxes.penaltycatalog.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PenaltyHistory(
    val _id: String = "",
    val penaltyName: String = "",
    val playerName: String = "",
    val penaltyValue: Int = 0,
    val penaltyIsBeer: Boolean = false,
    val timeOfPenalty: Instant = Clock.System.now(),
    val penaltyPaid: Boolean = false
)