package com.example.offlinefirstapp.data.remote.dataSources

import com.example.offlinefirstapp.data.remote.api.CoinPaprikaApi
import com.example.offlinefirstapp.data.remote.models.CoinRemoteModel
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val coinPaprikaApi: CoinPaprikaApi
){
    suspend fun getRemoteCoins(): List<CoinRemoteModel>{
        return coinPaprikaApi.getRemoteCoins()
    }

}