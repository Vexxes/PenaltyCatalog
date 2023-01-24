package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenaltyReceivedKtorApi {
    @GET("penalties-received")
    suspend fun getAllPenaltiesReceived(): List<PenaltyReceived>

    @GET("penalty-received/{penaltyReceivedId")
    suspend fun getPenaltyReceivedById(@Path("penaltyReceivedId") penaltyReceivedId: String): PenaltyReceived

    @GET("penalty-received-playerId/{playerId}")
    suspend fun getPenaltiesReceivedByPlayerId(@Path("playerId") playerId: String): List<PenaltyReceived>?

    @POST("penalty-received")
    suspend fun postPenaltyReceived(@Body penaltyReceived: PenaltyReceived): String?

    @PUT("penalty-received/{penaltyReceivedId}")
    suspend fun updatePenaltyReceived(@Path("penaltyReceivedId") id: String, @Body penaltyReceived: PenaltyReceived): Boolean

    @DELETE("penalty-received/{penaltyReceivedId}")
    suspend fun deletePenaltyReceived(@Path("penaltyReceivedId") id: String): Boolean
}