package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Player
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

// TODO Add functions from ktor server
interface KtorApi {

    @GET("get_players")
    suspend fun getAllPlayers(): ApiResponse

    @GET("get_player/{playerId}")
    suspend fun getPlayerById(@Path("playerId") playerId: String): ApiResponse

    @PUT("update_player")
    suspend fun updatePlayer(@Body player: Player): ApiResponse

    @PUT("delete_player/{playerId}")
    suspend fun deletePlayer(@Path("playerId") playerId: String): ApiResponse
}