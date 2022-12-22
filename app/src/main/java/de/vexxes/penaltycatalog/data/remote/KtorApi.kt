package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.PenaltyHistory
import de.vexxes.penaltycatalog.domain.model.Player
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// TODO Add functions from ktor server
interface KtorApi {

    @GET("get_players")
    suspend fun getAllPlayers(@Query("sortAscDesc") sortOrder: Int): ApiResponse

    @GET("get_player/{playerId}")
    suspend fun getPlayerById(@Path("playerId") playerId: String): ApiResponse

    @PUT("update_player")
    suspend fun updatePlayer(@Body player: Player): ApiResponse

    @PUT("delete_player/{playerId}")
    suspend fun deletePlayer(@Path("playerId") playerId: String): ApiResponse

    @GET("get_players_by_search")
    suspend fun getPlayersBySearch(@Query("searchText") searchText: String): ApiResponse



    @GET("get_categories")
    suspend fun getAllCategories(): ApiResponse

    @GET("get_penalties")
    suspend fun getAllPenalties(): ApiResponse

    @GET("get_penalty/{penaltyId}")
    suspend fun getPenaltyById(@Path("penaltyId") penaltyId: String): ApiResponse

    @GET("get_penalties_by_search")
    suspend fun getPenaltiesBySearch(@Query("searchText") searchText: String): ApiResponse

    @PUT("/update_penalty")
    suspend fun updatePenalty(@Body penalty: Penalty): ApiResponse

    @PUT("delete_penalty/{penaltyId}")
    suspend fun deletePenalty(@Path("penaltyId") penaltyId: String): ApiResponse



    @GET("get_penalty_history")
    suspend fun getAllPenaltyHistory(): ApiResponse

    @GET("get_penalty_history/{penaltyHistoryId}")
    suspend fun getPenaltyHistoryById(@Path("penaltyHistoryId") penaltyHistoryId: String): ApiResponse

    @GET("get_penalty_history_by_search")
    suspend fun getPenaltyHistoryBySearch(@Query("searchText") searchText: String): ApiResponse

    @PUT("/update_penaltyHistory")
    suspend fun updatePenaltyHistory(@Body penaltyHistory: PenaltyHistory): ApiResponse

    @PUT("delete_penalty_history/{penaltyHistoryId}")
    suspend fun deletePenaltyHistory(@Path("penaltyHistoryId") penaltyHistoryId: String): ApiResponse
}