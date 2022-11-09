package de.vexxes.penaltycatalog.data.repository

import dagger.hilt.android.scopes.ViewModelScoped
import de.vexxes.penaltycatalog.data.remote.KtorApi
import de.vexxes.penaltycatalog.domain.model.ApiResponse
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import javax.inject.Inject

@ViewModelScoped
class PlayerRepositoryImpl @Inject constructor(
    private val ktorApi: KtorApi
): PlayerRepository {

    // TODO Implement functions from KtorApi
    override suspend fun getAllPlayers(): ApiResponse {
        return try {
            ktorApi.getAllPlayers()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }
}