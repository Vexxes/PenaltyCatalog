package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.lang.Exception

@Serializable
data class ApiResponse(
    val success: Boolean = false,
    val penalty: Penalty? = null,
    val player: List<Player>? = null,
    val penaltyHistory: PenaltyHistory? = null,
    val cancellation: Cancellation? = null,
    val event: Event? = null,
    val message: String? = null,
    @Transient
    val error: Exception? = null
)