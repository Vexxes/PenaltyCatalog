package de.vexxes.penaltycatalog.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class PlayerState {
    PRESENT,
    CANCELED,
    PAID_BEER,
    NOT_PRESENT
}