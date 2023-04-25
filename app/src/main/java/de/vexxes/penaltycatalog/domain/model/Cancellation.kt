package de.vexxes.penaltycatalog.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Cancellation(
    val id: String = "",
    val playerId: String = "",
    val eventId: String = "",
    val timeOfCancellation: LocalDateTime
)