package de.vexxes.penaltycatalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.vexxes.penaltycatalog.data.remote.KtorApi
import de.vexxes.penaltycatalog.data.remote.PenaltyTypeKtorApi
import de.vexxes.penaltycatalog.data.remote.PlayerKtorApi
import de.vexxes.penaltycatalog.data.repository.PenaltyTypeRepositoryImpl
import de.vexxes.penaltycatalog.data.repository.PlayerRepositoryImpl
import de.vexxes.penaltycatalog.data.repository.RepositoryImpl
import de.vexxes.penaltycatalog.domain.repository.PenaltyTypeRepository
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import de.vexxes.penaltycatalog.domain.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        ktorApi: KtorApi
    ): Repository {
        return RepositoryImpl(
            ktorApi = ktorApi
        )
    }

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

}