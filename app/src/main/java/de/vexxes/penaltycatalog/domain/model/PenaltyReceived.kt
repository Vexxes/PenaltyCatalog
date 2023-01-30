package de.vexxes.penaltycatalog.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class PenaltyReceived(
    val id: String = "",
    val penaltyTypeId: String = "",
    val playerId: String = "",
    val timeOfPenalty: LocalDate,
    val timeOfPenaltyPaid: LocalDate? = null
)