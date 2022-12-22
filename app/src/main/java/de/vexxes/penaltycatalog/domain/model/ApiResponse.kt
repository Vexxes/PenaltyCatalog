package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.lang.Exception

@Serializable
data class ApiResponse(
    val success: Boolean = false,
    val penalty: List<Penalty>? = null,
    val penaltyCategory: List<PenaltyCategory>? = null,
    val player: List<Player>? = null,
    val penaltyHistory: List<PenaltyHistory>? = null,
    val cancellation: List<Cancellation>? = null,
    val event: List<Event>? = null,
    val message: String? = null,
    @Transient
    val error: Exception? = null
)