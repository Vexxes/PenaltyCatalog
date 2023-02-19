package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.enums.PlayerState
import de.vexxes.penaltycatalog.domain.model.Event

interface EventRepository {
    suspend fun getAllEvents(): List<Event>
    suspend fun getEventById(eventId: String): Event?
    suspend fun postEvent(event: Event): String?
    suspend fun updateEvent(eventId: String, event: Event): Boolean
    suspend fun playerEvent(eventId: String, playerAvailability: PlayerState): Boolean
    suspend fun deleteEvent(eventId: String): Boolean
}