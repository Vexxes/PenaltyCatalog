package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PenaltyHistory(
    val id: Int,
    val penalty: Penalty,
    val player: Player,
    val timeOfPenalty: String,
    val penaltyPaid: Boolean
)