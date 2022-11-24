package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.ApiResponse

// TODO Create needed functions as suspend
interface Repository {

    suspend fun getAllPlayers(): ApiResponse
    suspend fun getPlayerById(playerId: String): ApiResponse

}