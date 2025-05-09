package com.frontendmasters.campusoverflow.di

import com.frontendmasters.campusoverflow.api.ApiClient
import com.frontendmasters.campusoverflow.api.repository.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideApiRepository(): ApiRepository {
        return ApiRepository()
    }
} 