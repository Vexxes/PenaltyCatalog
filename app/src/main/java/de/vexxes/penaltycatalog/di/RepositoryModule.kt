package de.vexxes.penaltycatalog.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.vexxes.penaltycatalog.data.remote.KtorApi
import de.vexxes.penaltycatalog.data.repository.PlayerRepositoryImpl
import de.vexxes.penaltycatalog.domain.repository.PlayerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePlayerRepository(
        ktorApi: KtorApi
    ): PlayerRepository {
        return PlayerRepositoryImpl(
            ktorApi = ktorApi
        )
    }

}