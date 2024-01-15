package com.example.offlinefirstapp.data.repository

import com.example.offlinefirstapp.data.local.CoinEntity
import com.example.offlinefirstapp.data.models.Coin
import com.example.offlinefirstapp.data.remote.models.CoinRemoteModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
//    suspend fun getCoins(): List<Coin>
    suspend fun insertCoinEntity(coinEntity: CoinEntity)
    suspend fun getLocalCoins(): List<CoinEntity>
    fun getLocalCoinsWithFlow(): Flow<List<CoinEntity>>
    suspend fun getRemoteCoins(): List<CoinRemoteModel>

    suspend fun deleteAllCoinEntities()


}