package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int,
    val title: String,
    val startOfEvent: String,
    val startOfMeeting: String,
    val address: String,
    val description: String
)