package de.vexxes.penaltycatalog.data.repository

import android.util.Log
import de.vexxes.penaltycatalog.data.remote.CancellationKtorApi
import de.vexxes.penaltycatalog.domain.model.Cancellation
import de.vexxes.penaltycatalog.domain.repository.CancellationRepository
import javax.inject.Inject

class CancellationRepositoryImpl @Inject constructor(
    private val cancellationKtorApi: CancellationKtorApi
): CancellationRepository {
    override suspend fun getAllCancellations(): List<Cancellation> {
        return try {
            cancellationKtorApi.getAllCancellations()
        } catch (e: Exception) {
            Log.d("CancellationRepositoryImpl", e.stackTraceToString())
            emptyList()
        }
    }

    override suspend fun getCancellationById(cancellationId: String): Cancellation? {
        return try {
            cancellationKtorApi.getCancellationById(cancellationId = cancellationId)
        } catch (e: Exception) {
            Log.d("CancellationRepositoryImpl", e.stackTraceToString())
            null
        }
    }

    override suspend fun getCancellationsForEvent(eventId: String): List<Cancellation> {
        return try {
            cancellationKtorApi.getCancellationsForEvent(eventId = eventId)
        } catch (e: Exception) {
            Log.d("CancellationRepositoryImpl", e.stackTraceToString())
            emptyList()
        }
    }

    override suspend fun getCancellationsForPlayer(playerId: String): List<Cancellation> {
        return try {
            cancellationKtorApi.getCancellationsForPlayer(playerId = playerId)
        } catch (e: Exception) {
            Log.d("CancellationRepositoryImpl", e.stackTraceToString())
            emptyList()
        }
    }

    override suspend fun postCancellation(cancellation: Cancellation): String? {
        return try {
            cancellationKtorApi.postCancellation(cancellation = cancellation)
        } catch (e: Exception) {
            Log.d("CancellationRepositoryImpl", e.stackTraceToString())
            null
        }
    }

    override suspend fun updateCancellation(
        cancellationId: String,
        cancellation: Cancellation
    ): Boolean {
        return try {
            cancellationKtorApi.updateCancellation(cancellationId = cancellationId, cancellation = cancellation)
        } catch (e: Exception) {
            Log.d("CancellationRepositoryImpl", e.stackTraceToString())
            false
        }
    }

    override suspend fun deleteCancellation(cancellationId: String): Boolean {
        return try {
            cancellationKtorApi.deleteCancellation(cancellationId = cancellationId)
        } catch (e: Exception) {
            Log.d("CancellationRepositoryImpl", e.stackTraceToString())
            false
        }
    }
}