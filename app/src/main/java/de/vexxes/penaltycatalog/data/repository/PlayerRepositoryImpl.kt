package de.vexxes.penaltycatalog.data.repository

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import de.vexxes.penaltycatalog.data.remote.PlayerKtorApi
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import javax.inject.Inject

@ViewModelScoped
class PlayerRepositoryImpl @Inject constructor(
    private val playerKtorApi: PlayerKtorApi
): PlayerRepository {

    override suspend fun getAllPlayers(): List<Player> {
        return try {
            playerKtorApi.getAllPlayers()
        } catch (e: Exception) {
            Log.d("PlayerRepositoryImpl", e.toString())
            emptyList()
        }
    }

    override suspend fun getPlayerById(id: String): Player? {
        return try {
            playerKtorApi.getPlayerById(playerId = id)
        } catch (e: Exception) {
            Log.d("PlayerRepositoryImpl", e.toString())
            null
        }
    }

    override suspend fun getPlayersBySearch(name: String): List<Player>? {
        return try {
            playerKtorApi.getPlayersBySearch(searchText = name)
        } catch (e: Exception) {
            Log.d("PlayerRepositoryImpl", e.toString())
            emptyList()
        }
    }

    override suspend fun postPlayer(player: Player): String? {
        return try {
            playerKtorApi.postPlayer(player = player)
        } catch (e: Exception) {
            Log.d("PlayerRepositoryImpl", e.toString())
            null
        }
    }

    override suspend fun updatePlayer(id: String, player: Player): Boolean {
        return try {
            playerKtorApi.updatePlayer(id = id, player = player)
        } catch (e: Exception) {
            Log.d("PlayerRepositoryImpl", e.toString())
            false
        }
    }

    override suspend fun deletePlayer(id: String): Boolean {
        return try {
            playerKtorApi.deletePlayer(playerId = id)
        } catch (e: Exception) {
            Log.d("PlayerRepositoryImpl", e.toString())
            false
        }
    }
}