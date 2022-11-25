package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Player

// TODO Create needed functions as suspend
interface Repository {

    suspend fun getAllPlayers(): ApiResponse
    suspend fun getPlayerById(playerId: String): ApiResponse
    suspend fun updatePlayer(player: Player): ApiResponse
    suspend fun deletePlayer(playerId: String): ApiResponse

}