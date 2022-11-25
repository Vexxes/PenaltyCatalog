package de.vexxes.penaltycatalog.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import de.vexxes.penaltycatalog.data.remote.KtorApi
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.model.Player
import de.vexxes.penaltycatalog.domain.repository.Repository
import javax.inject.Inject

@ViewModelScoped
class RepositoryImpl @Inject constructor(
    private val ktorApi: KtorApi
): Repository {

    // TODO Implement functions from KtorApi
    override suspend fun getAllPlayers(): ApiResponse {
        return try {
            ktorApi.getAllPlayers()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getPlayerById(playerId: String): ApiResponse {
        return try {
            ktorApi.getPlayerById(playerId = playerId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun updatePlayer(player: Player): ApiResponse {
        return try {
            ktorApi.updatePlayer(player = player)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun deletePlayer(playerId: String): ApiResponse {
        return try {
            ktorApi.deletePlayer(playerId = playerId)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }
}