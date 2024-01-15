package com.example.offlinefirstapp.data.local.repository

import com.example.offlinefirstapp.data.local.CoinEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertCoinEntity(coinEntity: CoinEntity)

    suspend fun getAllCoinEntities():List<CoinEntity>

    fun getAllCoinEntitiesFlow(): Flow<List<CoinEntity>>

    suspend fun deleteAllCoinEntities()

}