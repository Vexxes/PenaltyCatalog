package de.vexxes.penaltycatalog.domain.repository

import de.vexxes.penaltycatalog.domain.model.Cancellation

interface CancellationRepository {
    suspend fun getAllCancellations(): List<Cancellation>
    suspend fun getCancellationById(cancellationId: String): Cancellation?
    suspend fun getCancellationsForEvent(eventId: String): List<Cancellation>
    suspend fun getCancellationsForPlayer(playerId: String): List<Cancellation>
    suspend fun postCancellation(cancellation: Cancellation): String?
    suspend fun updateCancellation(cancellationId: String, cancellation: Cancellation): Boolean
    suspend fun deleteCancellation(cancellationId: String): Boolean
}