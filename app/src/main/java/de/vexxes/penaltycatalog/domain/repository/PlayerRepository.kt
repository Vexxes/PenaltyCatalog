package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.Player

interface PlayerRepository {
    suspend fun getAllPlayers(): List<Player>
    suspend fun getPlayerById(id: String): Player?
    suspend fun getPlayersBySearch(name: String): List<Player>?
    suspend fun postPlayer(player: Player): String?
    suspend fun updatePlayer(id: String, player: Player): Boolean
    suspend fun deletePlayer(id: String): Boolean
}