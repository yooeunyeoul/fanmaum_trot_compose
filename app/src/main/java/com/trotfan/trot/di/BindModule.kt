package com.trotfan.trot.di

import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.impl.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    @Singleton
    abstract fun provideAuthService(authService: AuthServiceImpl): AuthService
}