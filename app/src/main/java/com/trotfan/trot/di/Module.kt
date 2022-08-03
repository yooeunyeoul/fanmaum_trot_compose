package com.trotfan.trot.di

import android.content.ContentValues
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    const val baseUrl = "api.thecatapi.com/v1"

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient(CIO) {
            defaultRequest {
                headers {
                    append("Content-type", "application/json")
//                    append(HttpHeaders.Authorization, "Client-ID id 값")
                }
                url {
                    protocol = URLProtocol.HTTPS
                    host = baseUrl
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d(ContentValues.TAG, message)
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000L
                connectTimeoutMillis = 10_000L
                socketTimeoutMillis = 10_000L
            }
        }
    }
}