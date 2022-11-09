package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.ApiResponse

// TODO Create needed functions as suspend
interface PlayerRepository {

    suspend fun getAllPlayers(): ApiResponse

}