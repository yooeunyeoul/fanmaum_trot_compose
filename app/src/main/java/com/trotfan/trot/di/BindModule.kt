package com.trotfan.trot.di

import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.network.impl.SignUpServiceImpl
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.HomeService
import com.trotfan.trot.network.ServerStateService
import com.trotfan.trot.network.VoteService
import com.trotfan.trot.network.impl.AuthServiceImpl
import com.trotfan.trot.network.impl.HomeServiceImpl
import com.trotfan.trot.network.impl.ServerStateServiceImpl
import com.trotfan.trot.network.impl.VoteServiceImpl
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
}