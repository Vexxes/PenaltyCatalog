package de.vexxes.penaltycatalog.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable


@Serializable
data class Event(
    val id: String = "",
    val title: String = "",
    val dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val meetingTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val address: String = "",
    val description: String = ""
)