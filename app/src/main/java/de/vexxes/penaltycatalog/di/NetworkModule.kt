package de.vexxes.penaltycatalog.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.vexxes.penaltycatalog.data.remote.CancellationKtorApi
import de.vexxes.penaltycatalog.data.remote.EventKtorApi
import de.vexxes.penaltycatalog.data.remote.PenaltyReceivedKtorApi
import de.vexxes.penaltycatalog.data.remote.PenaltyTypeKtorApi
import de.vexxes.penaltycatalog.data.remote.PlayerKtorApi
import de.vexxes.penaltycatalog.util.BASE_URL
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCookieManager(): CookieManager {
        return CookieManager()
    }

    @Provides
    @Singleton
    fun provideHttpClient(cookieManager: CookieManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor("Bearer","Admin"))
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun providePenaltyTypeKtorApi(retrofit: Retrofit): PenaltyTypeKtorApi {
        return retrofit.create(PenaltyTypeKtorApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlayerKtorApi(retrofit: Retrofit): PlayerKtorApi {
        return retrofit.create(PlayerKtorApi::class.java)
    }

    @Provides
    @Singleton
    fun providePenaltyReceivedKtorApi(retrofit: Retrofit): PenaltyReceivedKtorApi {
        return retrofit.create(PenaltyReceivedKtorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventKtorApi(retrofit: Retrofit): EventKtorApi {
        return retrofit.create(EventKtorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCancellationKtorApi(retrofit: Retrofit): CancellationKtorApi {
        return retrofit.create(CancellationKtorApi::class.java)
    }

}

class OAuthInterceptor(
    private val tokenType: String,
    private val accessToken: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $accessToken").build()

        return chain.proceed(request)
    }
}