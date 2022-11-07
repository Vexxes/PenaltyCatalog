package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Penalty(
    val id: Int,
    val nameOfPenalty: String,
    val nameOfPenaltyCategory: String,
    val value: Float
)