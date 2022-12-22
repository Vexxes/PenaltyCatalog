package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.PenaltyHistory
import de.vexxes.penaltycatalog.domain.model.Player

// TODO Create needed functions as suspend
interface Repository {

    suspend fun getAllPlayers(sortOrder: Int = 1): ApiResponse
    suspend fun getPlayerById(playerId: String): ApiResponse
    suspend fun updatePlayer(player: Player): ApiResponse
    suspend fun deletePlayer(playerId: String): ApiResponse
    suspend fun getPlayersBySearch(searchText: String): ApiResponse



    suspend fun getAllCategories(): ApiResponse
    suspend fun getAllPenalties(): ApiResponse
    suspend fun getPenaltyById(penaltyId: String): ApiResponse
    suspend fun getPenaltiesBySearch(searchText: String): ApiResponse
    suspend fun updatePenalty(penalty: Penalty): ApiResponse
    suspend fun deletePenalty(penaltyId: String): ApiResponse



    suspend fun getAllPenaltyHistory(): ApiResponse
    suspend fun getPenaltyHistoryById(penaltyHistoryId: String): ApiResponse
    suspend fun getPenaltyHistoryBySearch(searchText: String): ApiResponse
    suspend fun updatePenaltyHistory(penaltyHistory: PenaltyHistory): ApiResponse
    suspend fun deletePenaltyHistory(penaltyHistoryId: String): ApiResponse
}