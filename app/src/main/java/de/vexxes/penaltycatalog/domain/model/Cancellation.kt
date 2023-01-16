package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Cancellation(
    val id: String = "",
    val playerId: String = "",
    @Contextual val timeOfCancellation: LocalDate = LocalDate.now(),
    val eventId: String = ""
)