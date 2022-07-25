package com.trotfan.trot.di

import android.content.ContentValues
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
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
            install(JsonFeature) {
                serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true //모델에 없고, json 에 있는경우 해당 key 무시
                    prettyPrint = true
                    isLenient = true // "" 따옴표 잘못된건 무시하고 처리
                    encodeDefaults = false // null 인 값도 json 에 포함 시킨다.
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