package de.vexxes.penaltycatalog.data.repository

import android.util.Log
import de.vexxes.penaltycatalog.data.remote.PenaltyReceivedKtorApi
import de.vexxes.penaltycatalog.domain.model.PenaltyReceived
import de.vexxes.penaltycatalog.domain.repository.PenaltyReceivedRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class PenaltyReceivedRepositoryImpl @Inject constructor(
    private val penaltyReceivedKtorApi: PenaltyReceivedKtorApi
): PenaltyReceivedRepository {
    override suspend fun getAllPenaltyReceived(): List<PenaltyReceived> {
        return try {
            penaltyReceivedKtorApi.getAllPenaltiesReceived()
        } catch (e: Exception) {
            Log.d("PenaltyReceivedRepositoryImpl", e.stackTraceToString())
            emptyList()
        }
    }

    override suspend fun getPenaltyReceivedById(penaltyReceivedId: String): PenaltyReceived? {
        return try {
            penaltyReceivedKtorApi.getPenaltyReceivedById(penaltyReceivedId = penaltyReceivedId)
        } catch (e: Exception) {
            Log.d("PenaltyReceivedRepositoryImpl", e.toString())
            null
        }
    }

    override suspend fun getPenaltyReceivedByPlayerId(playerId: String): List<PenaltyReceived>? {
        return try {
            penaltyReceivedKtorApi.getPenaltiesReceivedByPlayerId(playerId = playerId)
        } catch (e: Exception) {
            Log.d("PenaltyReceivedRepositoryImpl", e.toString())
            emptyList()
        }
    }

    override suspend fun postPenaltyReceived(penaltyReceived: PenaltyReceived): String? {
        return try {
            penaltyReceivedKtorApi.postPenaltyReceived(penaltyReceived = penaltyReceived)
        } catch (e: Exception) {
            Log.d("PenaltyReceivedRepositoryImpl", e.toString())
            null
        }
    }

    override suspend fun updatePenaltyReceived(id: String, penaltyReceived: PenaltyReceived): Boolean {
        return try {
            penaltyReceivedKtorApi.updatePenaltyReceived(id = id, penaltyReceived = penaltyReceived)
        } catch (e: Exception) {
            Log.d("PenaltyReceivedRepositoryImpl", e.toString())
            false
        }
    }

    override suspend fun deletePenaltyReceived(penaltyReceivedId: String): Boolean {
        return try {
            penaltyReceivedKtorApi.deletePenaltyReceived(id = penaltyReceivedId)
        } catch (e: Exception) {
            Log.d("PenaltyReceivedRepositoryImpl", e.toString())
            false
        }
    }
}