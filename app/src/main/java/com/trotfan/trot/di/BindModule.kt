package com.trotfan.trot.di

import com.trotfan.trot.network.ApiServiceOne
import com.trotfan.trot.network.ApiServiceOneImpl
import com.trotfan.trot.network.ApiServiceTwo
import com.trotfan.trot.network.ApiServiceTwoImpl
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
    abstract fun provideServiceOne(apiServiceOne: ApiServiceOneImpl): ApiServiceOne

    @Binds
    @Singleton
    abstract fun provideServiceTwo(apiServiceTwo: ApiServiceTwoImpl): ApiServiceTwo
}