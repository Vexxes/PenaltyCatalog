package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Penalty(
    val _id: String = "",
    val name: String = "",
    val categoryName: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: Int = 0,
    val index: Int = 0
)