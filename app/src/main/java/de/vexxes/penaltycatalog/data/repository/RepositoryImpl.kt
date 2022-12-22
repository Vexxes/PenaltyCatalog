package de.vexxes.penaltycatalog.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import de.vexxes.penaltycatalog.data.remote.KtorApi
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.PenaltyHistory
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.Repository
import javax.inject.Inject

@ViewModelScoped
class RepositoryImpl @Inject constructor(
    private val ktorApi: KtorApi
): Repository {

    // TODO Implement functions from KtorApi
    override suspend fun getAllPlayers(sortOrder: Int): ApiResponse {
        return try {
            ktorApi.getAllPlayers(sortOrder = sortOrder)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getPlayerById(playerId: String): ApiResponse {
        return try {
            ktorApi.getPlayerById(playerId = playerId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun updatePlayer(player: Player): ApiResponse {
        return try {
            ktorApi.updatePlayer(player = player)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun deletePlayer(playerId: String): ApiResponse {
        return try {
            ktorApi.deletePlayer(playerId = playerId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getPlayersBySearch(searchText: String): ApiResponse {
        return try {
            ktorApi.getPlayersBySearch(searchText = searchText)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }



    override suspend fun getAllCategories(): ApiResponse {
        return try {
            ktorApi.getAllCategories()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getAllPenalties(): ApiResponse {
        return try {
            ktorApi.getAllPenalties()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getPenaltyById(penaltyId: String): ApiResponse {
        return try {
            ktorApi.getPenaltyById(penaltyId = penaltyId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getPenaltiesBySearch(searchText: String): ApiResponse {
        return try {
            ktorApi.getPenaltiesBySearch(searchText = searchText)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun updatePenalty(penalty: Penalty): ApiResponse {
        return try {
            ktorApi.updatePenalty(penalty = penalty)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun deletePenalty(penaltyId: String): ApiResponse {
        return try {
            ktorApi.deletePenalty(penaltyId = penaltyId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getAllPenaltyHistory(): ApiResponse {
        return try {
            ktorApi.getAllPenaltyHistory()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getPenaltyHistoryById(penaltyHistoryId: String): ApiResponse {
        return try {
            ktorApi.getPenaltyHistoryById(penaltyHistoryId = penaltyHistoryId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getPenaltyHistoryBySearch(searchText: String): ApiResponse {
        return try {
            ktorApi.getPenaltyHistoryBySearch(searchText = searchText)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun updatePenaltyHistory(penaltyHistory: PenaltyHistory): ApiResponse {
        return try {
            ktorApi.updatePenaltyHistory(penaltyHistory = penaltyHistory)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun deletePenaltyHistory(penaltyHistoryId: String): ApiResponse {
        return try {
            ktorApi.deletePenaltyHistory(penaltyHistoryId = penaltyHistoryId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }
}