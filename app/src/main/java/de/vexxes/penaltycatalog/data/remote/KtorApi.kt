package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import retrofit2.http.GET

// TODO Add functions from ktor server
interface KtorApi {

    @GET("get_players")
    suspend fun getAllPlayers(): ApiResponse

}