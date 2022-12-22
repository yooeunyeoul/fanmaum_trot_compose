package com.trotfan.trot.di

import com.trotfan.trot.network.*
import com.trotfan.trot.network.impl.*
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
    abstract fun provideSignUpService(signUpService: SignUpServiceImpl): SignUpService

    @Binds
    @Singleton
    abstract fun provideAuthService(authService: AuthServiceImpl): AuthService

    @Binds
    @Singleton
    abstract fun serverStateService(serverStateService: ServerStateServiceImpl): ServerStateService

    @Binds
    @Singleton
    abstract fun homeService(homeService: HomeServiceImpl): HomeService

    @Binds
    @Singleton
    abstract fun provideVoteService(serverStateService: VoteServiceImpl): VoteService

    @Binds
    @Singleton
    abstract fun provideRankService(rankService: RankServiceImpl): RankService
}