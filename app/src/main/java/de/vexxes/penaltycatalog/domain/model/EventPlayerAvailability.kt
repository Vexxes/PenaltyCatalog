package de.vexxes.penaltycatalog.domain.model

import de.vexxes.penaltycatalog.domain.enums.PlayerState
import kotlinx.serialization.Serializable

@Serializable
data class EventPlayerAvailability(
    val playerId: String,
    val playerState: PlayerState
)