package de.vexxes.penaltycatalog.data.repository

import android.util.Log
import de.vexxes.penaltycatalog.data.remote.EventKtorApi
import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.EventPlayerAvailability
import de.vexxes.penaltycatalog.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventKtorApi: EventKtorApi
): EventRepository {
    override suspend fun getAllEvents(): List<Event> {
        return try {
            eventKtorApi.getAllEvents()
        } catch (e: Exception) {
            Log.d("EventRepositoryImpl", e.stackTraceToString())
            emptyList()
        }
    }

    override suspend fun getEventById(eventId: String): Event? {
        return try {
            eventKtorApi.getEventById(eventId = eventId)
        } catch (e: Exception) {
            Log.d("EventRepositoryImpl", e.stackTraceToString())
            null
        }
    }

    override suspend fun postEvent(event: Event): String? {
        return try {
            eventKtorApi.postEvent(event = event)
        } catch (e: Exception) {
            Log.d("EventRepositoryImpl", e.stackTraceToString())
            null
        }
    }

    override suspend fun updateEvent(eventId: String, event: Event): Boolean {
        return try {
            eventKtorApi.updateEvent(eventId = eventId, event = event)
        } catch (e: Exception) {
            Log.d("EventRepositoryImpl", e.stackTraceToString())
            false
        }
    }

    override suspend fun playerEvent(eventId: String, playerAvailability: EventPlayerAvailability): Boolean {
        return try {
            eventKtorApi.playerEvent(eventId = eventId, playerAvailability = playerAvailability)
        } catch (e: Exception) {
            Log.d("EventRepositoryImpl", e.stackTraceToString())
            false
        }
    }

    override suspend fun deleteEvent(eventId: String): Boolean {
        return try {
            eventKtorApi.deleteEvent(eventId = eventId)
        } catch (e: Exception) {
            Log.d("EventRepositoryImpl", e.stackTraceToString())
            false
        }
    }
}