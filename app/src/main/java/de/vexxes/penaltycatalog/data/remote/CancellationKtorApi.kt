package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.Cancellation
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CancellationKtorApi {
    @GET("cancellation")
    suspend fun getAllCancellations(): List<Cancellation>

    @GET("cancellation/{cancellationId}")
    suspend fun getCancellationById(@Path("cancellationId") cancellationId: String): Cancellation

    @GET("cancellation/event/{eventId}")
    suspend fun getCancellationsForEvent(@Path("eventId") eventId: String): List<Cancellation>

    @GET("cancellation/player/{playerId}")
    suspend fun getCancellationsForPlayer(@Path("playerId") playerId: String): List<Cancellation>

    @POST("cancellation")
    suspend fun postCancellation(@Body cancellation: Cancellation): String?

    @PUT("cancellation/{cancellationId}")
    suspend fun updateCancellation(@Path("cancellationId") cancellationId: String, @Body cancellation: Cancellation): Boolean

    @DELETE("cancellation/{cancellationId}")
    suspend fun deleteCancellation(@Path("cancellationId") cancellationId: String): Boolean
}