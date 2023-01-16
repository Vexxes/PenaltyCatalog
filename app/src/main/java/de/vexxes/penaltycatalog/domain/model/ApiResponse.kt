package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.lang.Exception

@Serializable
data class ApiResponse(
    val success: Boolean = false,
    val penaltyReceived: List<PenaltyReceived>? = null,
    val cancellation: List<Cancellation>? = null,
    val event: List<Event>? = null,
    val message: String? = null,
    @Transient
    val error: Exception? = null
)