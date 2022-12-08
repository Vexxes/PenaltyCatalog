package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Penalty(
    val _id: String = "",
    val name: String = "",
    val nameOfCategory: String = "",
    val description: String = "",
    val isBeer: Boolean = false,
    val value: Int = 0      // if value is beer, then the value is money and stored as cents. For correct visualisation is has to be divided by 100
)