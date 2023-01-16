package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived

interface Repository {
    suspend fun getAllPenaltyHistory(): ApiResponse
    suspend fun getPenaltyHistoryById(penaltyHistoryId: String): ApiResponse
    suspend fun getPenaltyHistoryBySearch(searchText: String): ApiResponse
    suspend fun updatePenaltyHistory(penaltyReceived: PenaltyReceived): ApiResponse
    suspend fun deletePenaltyHistory(penaltyHistoryId: String): ApiResponse
}