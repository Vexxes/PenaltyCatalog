package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// TODO Add functions from ktor server
interface KtorApi {
    @GET("get_penalty_history")
    suspend fun getAllPenaltyHistory(): ApiResponse

    @GET("get_penalty_history/{penaltyHistoryId}")
    suspend fun getPenaltyHistoryById(@Path("penaltyHistoryId") penaltyHistoryId: String): ApiResponse

    @GET("get_penalty_history_by_search")
    suspend fun getPenaltyHistoryBySearch(@Query("searchText") searchText: String): ApiResponse

    @PUT("/update_penaltyHistory")
    suspend fun updatePenaltyHistory(@Body penaltyReceived: PenaltyReceived): ApiResponse

    @PUT("delete_penalty_history/{penaltyHistoryId}")
    suspend fun deletePenaltyHistory(@Path("penaltyHistoryId") penaltyHistoryId: String): ApiResponse
}