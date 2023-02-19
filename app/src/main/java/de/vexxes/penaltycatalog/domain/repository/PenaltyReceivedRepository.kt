package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.PenaltyReceived

interface PenaltyReceivedRepository {
    suspend fun getAllPenaltyReceived(): List<PenaltyReceived>
    suspend fun getPenaltyReceivedById(penaltyReceivedId: String): PenaltyReceived?
    suspend fun getPenaltyReceivedByPlayerId(playerId: String): List<PenaltyReceived>?
    suspend fun postPenaltyReceived(penaltyReceived: PenaltyReceived): String?
    suspend fun updatePenaltyReceived(id: String, penaltyReceived: PenaltyReceived): Boolean
    suspend fun deletePenaltyReceived(penaltyReceivedId: String): Boolean
}