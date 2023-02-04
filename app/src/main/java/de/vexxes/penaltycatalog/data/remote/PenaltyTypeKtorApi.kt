package de.vexxes.penaltycatalog.data.remote

import de.vexxes.penaltycatalog.domain.model.PenaltyType
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PenaltyTypeKtorApi {

    @GET("penalty-types")
    suspend fun getAllPenaltyTypes(): List<PenaltyType>

    @GET("/penalty-type/{penaltyTypeId}")
    suspend fun getPenaltyTypeById(@Path("penaltyTypeId") penaltyTypeId: String): PenaltyType

    @GET("/penalty-type-search")
    suspend fun getPenaltyTypesBySearch(@Query("name") name: String): List<PenaltyType>?

    @POST("penalty-type")
    suspend fun postPenaltyType(@Body penaltyType: PenaltyType): String?

    @PUT("/penalty-type/{penaltyTypeId}")
    suspend fun updatePenaltyType(@Path("penaltyTypeId") id: String, @Body penaltyType: PenaltyType): Boolean

    @DELETE("/penalty-type/{penaltyTypeId}")
    suspend fun deletePenaltyType(@Path("penaltyTypeId") penaltyTypeId: String): Boolean
}