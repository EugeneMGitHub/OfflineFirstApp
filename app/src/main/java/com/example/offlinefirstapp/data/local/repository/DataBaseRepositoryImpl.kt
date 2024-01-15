package com.example.offlinefirstapp.data.local.repository

import com.example.offlinefirstapp.data.local.CoinEntity
import com.example.offlinefirstapp.data.local.DatabaseDao
import kotlinx.coroutines.flow.Flow

class DataBaseRepositoryImpl(
    private val databaseDao: DatabaseDao
) : DatabaseRepository{
    override suspend fun insertCoinEntity(coinEntity: CoinEntity) {
        databaseDao.insertCoinEntity(coinEntity)
    }

    override suspend fun getAllCoinEntities(): List<CoinEntity> {
        return databaseDao.getAllCoinEntities()
    }



    override fun getAllCoinEntitiesFlow(): Flow<List<CoinEntity>> {
        return databaseDao.getAllCoinEntitiesFlow()
    }

    override suspend fun deleteAllCoinEntities() {
        databaseDao.deleteAllCoinEntities()
    }

}