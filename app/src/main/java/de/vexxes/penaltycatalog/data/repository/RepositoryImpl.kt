package de.vexxes.penaltycatalog.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import de.vexxes.penaltycatalog.data.remote.KtorApi
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import de.vexxes.penaltycatalog.domain.repository.Repository
import javax.inject.Inject

@ViewModelScoped
class RepositoryImpl @Inject constructor(
    private val ktorApi: KtorApi
): Repository {
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