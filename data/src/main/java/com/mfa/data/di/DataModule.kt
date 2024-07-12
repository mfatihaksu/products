package com.mfa.data.di

import com.mfa.data.data.ProductRepository
import com.mfa.data.helper.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideApiClient() = ApiClient()

    @Singleton
    @Provides
    fun provideProductRepository(apiClient: ApiClient) = ProductRepository(apiClient)
}