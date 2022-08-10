package com.trotfan.trot.di

import com.trotfan.trot.network.SignUpService
import com.trotfan.trot.network.impl.SignUpServiceImpl
import com.trotfan.trot.network.ApiServiceTwo
import com.trotfan.trot.network.impl.ApiServiceTwoImpl
import com.trotfan.trot.network.AuthService
import com.trotfan.trot.network.InvitationService
import com.trotfan.trot.network.impl.AuthServiceImpl
import com.trotfan.trot.network.impl.InvitationServiceImpl
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
    abstract fun provideServiceTwo(apiServiceTwo: ApiServiceTwoImpl): ApiServiceTwo

    @Binds
    @Singleton
    abstract fun provideAuthService(authService: AuthServiceImpl): AuthService

    @Binds
    @Singleton
    abstract fun provideInvitationService(invitationService: InvitationServiceImpl): InvitationService
}