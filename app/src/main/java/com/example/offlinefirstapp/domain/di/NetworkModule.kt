package com.example.offlinefirstapp.domain.di

import com.example.offlinefirstapp.common.constants.Constants.BASE_URL
import com.example.offlinefirstapp.data.remote.api.CoinPaprikaApi
import com.example.offlinefirstapp.data.remote.api.RetrofitCoinPaprikaApi
import com.example.offlinefirstapp.data.remote.dataSources.CoinRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().build()

        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinPaprikaApi(retrofit: Retrofit): CoinPaprikaApi {
        return retrofit.create(RetrofitCoinPaprikaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRemoteDataSource(coinPaprikaApi: CoinPaprikaApi) : CoinRemoteDataSource {
        return CoinRemoteDataSource(coinPaprikaApi = coinPaprikaApi)
    }



}