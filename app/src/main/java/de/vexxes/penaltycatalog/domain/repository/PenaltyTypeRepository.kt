package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.PenaltyType

interface PenaltyTypeRepository {
    suspend fun getAllPenaltyTypes(): List<PenaltyType>
    suspend fun getPenaltyTypeById(penaltyTypeId: String): PenaltyType?
    suspend fun getPenaltyTypesBySearch(name: String): List<PenaltyType>?
    suspend fun postPenaltyType(penaltyType: PenaltyType): String?
    suspend fun updatePenaltyType(id: String, penaltyType: PenaltyType): Boolean
    suspend fun deletePenaltyType(penaltyTypeId: String): Boolean
}