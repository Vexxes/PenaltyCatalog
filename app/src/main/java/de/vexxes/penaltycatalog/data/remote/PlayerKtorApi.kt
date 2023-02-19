package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.Player
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayerKtorApi {
    @GET("players")
    suspend fun getAllPlayers(): List<Player>

    @GET("player/{playerId}")
    suspend fun getPlayerById(@Path("playerId") playerId: String): Player

    @GET("player-search")
    suspend fun getPlayersBySearch(@Query("name") searchText: String): List<Player>?

    @POST("player")
    suspend fun postPlayer(@Body player: Player): String?

    @PUT("player/{playerId}")
    suspend fun updatePlayer(@Path("playerId") playerId: String, @Body player: Player): Boolean

    @DELETE("player/{playerId}")
    suspend fun deletePlayer(@Path("playerId") playerId: String): Boolean
}