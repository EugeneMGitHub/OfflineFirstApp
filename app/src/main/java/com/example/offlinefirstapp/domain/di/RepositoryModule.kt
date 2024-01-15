package com.example.offlinefirstapp.domain.di

import com.example.offlinefirstapp.data.local.repository.DatabaseRepository
import com.example.offlinefirstapp.data.remote.dataSources.CoinRemoteDataSource
import com.example.offlinefirstapp.data.repository.CoinRepository
import com.example.offlinefirstapp.data.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinRepository(
        coinRemoteDataSource: CoinRemoteDataSource,
        databaseRepository: DatabaseRepository
    ): CoinRepository{
        return CoinRepositoryImpl(
            coinRemoteDataSource = coinRemoteDataSource,
            coinDatabaseRepository = databaseRepository
        )
    }
}
