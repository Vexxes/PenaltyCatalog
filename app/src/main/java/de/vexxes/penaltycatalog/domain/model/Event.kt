package de.vexxes.penaltycatalog.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class Event(
    val id: String = "",
    val title: String = "",
    @Contextual val dateTime: LocalDateTime = LocalDateTime.now(),
    @Contextual val meetingTime: LocalTime = LocalTime.now(),
    val address: String = "",
    val description: String = ""
)