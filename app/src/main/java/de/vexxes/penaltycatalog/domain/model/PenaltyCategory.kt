package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PenaltyCategory(
    val _id: String = "",
    val name: String = ""
)