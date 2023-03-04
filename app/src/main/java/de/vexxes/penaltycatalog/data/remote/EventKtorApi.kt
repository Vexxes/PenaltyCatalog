package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.Event
import de.vexxes.penaltycatalog.domain.model.EventPlayerAvailability
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventKtorApi {
    @GET("events")
    suspend fun getAllEvents(): List<Event>

    @GET("event/{eventId}")
    suspend fun getEventById(@Path("eventId") eventId: String): Event

    @POST("event")
    suspend fun postEvent(@Body event: Event): String?

    @PUT("event/{eventId}")
    suspend fun updateEvent(@Path("eventId") eventId: String, @Body event: Event): Boolean

    @PUT("event-player/{eventId}")
    suspend fun playerEvent(@Path("eventId") eventId: String, @Body playerAvailability: EventPlayerAvailability): Boolean

    @DELETE("event/{eventId}")
    suspend fun deleteEvent(@Path("eventId") eventId: String): Boolean
}