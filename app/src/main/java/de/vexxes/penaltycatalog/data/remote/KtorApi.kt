package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

// TODO Add functions from ktor server
interface KtorApi {

    @GET("get_players")
    suspend fun getAllPlayers(): ApiResponse

    @GET("get_player/{playerId}")
    suspend fun getPlayerById(@Path("playerId") playerId: String): ApiResponse

}