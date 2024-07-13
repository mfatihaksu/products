package com.mfa.data.di

import android.content.Context
import androidx.room.Room
import com.mfa.data.data.AppDatabase
import com.mfa.data.data.ProductLocalDataSource
import com.mfa.data.data.ProductRemoteDataSource
import com.mfa.data.data.ProductRepository
import com.mfa.data.helper.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "product-db").build()

    @Singleton
    @Provides
    fun provideProductLocalDataSource(appDatabase: AppDatabase) =
        ProductLocalDataSource(appDatabase)

    @Singleton
    @Provides
    fun provideApiClient() = ApiClient()

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(apiClient: ApiClient) = ProductRemoteDataSource(apiClient)

    @Singleton
    @Provides
    fun provideProductRepository(localDataSource: ProductLocalDataSource, remoteDataSource: ProductRemoteDataSource) =
        ProductRepository(localDataSource, remoteDataSource)
}