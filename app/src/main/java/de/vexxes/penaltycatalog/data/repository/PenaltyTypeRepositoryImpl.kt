package de.vexxes.penaltycatalog.data.repository

import android.util.Log
import de.vexxes.penaltycatalog.data.remote.PenaltyTypeKtorApi
import de.vexxes.penaltycatalog.domain.model.PenaltyType
import de.vexxes.penaltycatalog.domain.repository.PenaltyTypeRepository
import javax.inject.Inject

class PenaltyTypeRepositoryImpl @Inject constructor(
    private val penaltyTypeKtorApi: PenaltyTypeKtorApi
): PenaltyTypeRepository {
    override suspend fun getAllPenaltyTypes(): List<PenaltyType> {
        return try {
            penaltyTypeKtorApi.getAllPenaltyTypes()
        } catch (e: Exception) {
            Log.d("PenaltyTypeRepositoryImpl", e.toString())
            emptyList()
        }
    }

    override suspend fun getPenaltyTypeById(penaltyTypeId: String): PenaltyType? {
        return try {
            penaltyTypeKtorApi.getPenaltyTypeById(penaltyTypeId = penaltyTypeId)
        } catch (e: Exception) {
            Log.d("PenaltyTypeRepositoryImpl", e.toString())
            null
        }
    }

    override suspend fun getPenaltyTypesBySearch(name: String): List<PenaltyType>? {
        return try {
            penaltyTypeKtorApi.getPenaltyTypesBySearch(name = name)
        } catch (e: Exception) {
            Log.d("PenaltyTypeRepositoryImpl", e.toString())
            emptyList()
        }
    }

    override suspend fun postPenaltyType(penaltyType: PenaltyType): String? {
        return try {
            penaltyTypeKtorApi.postPenaltyType(penaltyType = penaltyType)
        } catch (e: Exception) {
            Log.d("PenaltyTypeRepositoryImpl", e.toString())
            null
        }
    }

    override suspend fun updatePenaltyType(id: String, penaltyType: PenaltyType): Boolean {
        return try {
            penaltyTypeKtorApi.updatePenaltyType(id = id, penaltyType = penaltyType)
        } catch (e: Exception) {
            Log.d("PenaltyTypeRepositoryImpl", e.toString())
            false
        }
    }

    override suspend fun deletePenaltyType(penaltyTypeId: String): Boolean {
        return try {
            penaltyTypeKtorApi.deletePenaltyType(penaltyTypeId = penaltyTypeId)
        } catch (e: Exception) {
            Log.d("PenaltyTypeRepositoryImpl", e.toString())
            false
        }
    }
}