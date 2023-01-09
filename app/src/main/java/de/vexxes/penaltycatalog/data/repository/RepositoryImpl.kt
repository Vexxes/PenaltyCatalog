package de.vexxes.penaltycatalog.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import de.vexxes.penaltycatalog.data.remote.KtorApi
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Penalty
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.Repository
import javax.inject.Inject

@ViewModelScoped
class RepositoryImpl @Inject constructor(
    private val ktorApi: KtorApi
): Repository {

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

    override suspend fun getDeclaredPenalties(penaltyName: String): ApiResponse {
        return try {
            ktorApi.getDeclaredPenalties(penaltyName = penaltyName)
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

    override suspend fun updatePenaltyHistory(penaltyReceived: PenaltyReceived): ApiResponse {
        return try {
            ktorApi.updatePenaltyHistory(penaltyReceived = penaltyReceived)
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