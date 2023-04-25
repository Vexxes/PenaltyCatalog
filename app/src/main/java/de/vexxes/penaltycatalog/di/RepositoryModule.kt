package de.vexxes.penaltycatalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.vexxes.penaltycatalog.data.remote.CancellationKtorApi
import de.vexxes.penaltycatalog.data.remote.EventKtorApi
import de.vexxes.penaltycatalog.data.remote.PenaltyReceivedKtorApi
import de.vexxes.penaltycatalog.data.remote.PenaltyTypeKtorApi
import de.vexxes.penaltycatalog.data.remote.PlayerKtorApi
import de.vexxes.penaltycatalog.data.repository.CancellationRepositoryImpl
import de.vexxes.penaltycatalog.data.repository.EventRepositoryImpl
import de.vexxes.penaltycatalog.data.repository.PenaltyReceivedRepositoryImpl
import de.vexxes.penaltycatalog.data.repository.PenaltyTypeRepositoryImpl
import de.vexxes.penaltycatalog.data.repository.PlayerRepositoryImpl
import de.vexxes.penaltycatalog.domain.repository.CancellationRepository
import de.vexxes.penaltycatalog.domain.repository.EventRepository
import de.vexxes.penaltycatalog.domain.repository.PenaltyReceivedRepository
import de.vexxes.penaltycatalog.domain.repository.PenaltyTypeRepository
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePenaltyTypeRepository(
        penaltyTypeKtorApi: PenaltyTypeKtorApi
    ): PenaltyTypeRepository {
        return PenaltyTypeRepositoryImpl(
            penaltyTypeKtorApi = penaltyTypeKtorApi
        )
    }

    @Provides
    @Singleton
    fun providePlayerRepository(
        playerKtorApi: PlayerKtorApi
    ): PlayerRepository {
        return PlayerRepositoryImpl(
            playerKtorApi = playerKtorApi
        )
    }

    @Provides
    @Singleton
    fun providePenaltyReceivedRepository(
        penaltyReceivedKtorApi: PenaltyReceivedKtorApi
    ): PenaltyReceivedRepository {
        return PenaltyReceivedRepositoryImpl(
            penaltyReceivedKtorApi = penaltyReceivedKtorApi
        )
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        eventKtorApi: EventKtorApi
    ): EventRepository {
        return EventRepositoryImpl(
            eventKtorApi = eventKtorApi
        )
    }

    @Provides
    @Singleton
    fun provideCancellationRepository(
        cancellationKtorApi: CancellationKtorApi
    ): CancellationRepository {
        return CancellationRepositoryImpl(
            cancellationKtorApi = cancellationKtorApi
        )
    }
}