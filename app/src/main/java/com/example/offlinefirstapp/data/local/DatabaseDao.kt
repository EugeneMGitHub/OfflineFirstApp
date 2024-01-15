package com.example.offlinefirstapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinEntity(coinEntity: CoinEntity)

    @Query("SELECT * FROM CoinEntity")
    suspend fun getAllCoinEntities(): List<CoinEntity>

    @Query("SELECT * FROM CoinEntity")
    fun getAllCoinEntitiesFlow(): Flow<List<CoinEntity>>

    @Query("DELETE FROM CoinEntity")
    suspend fun deleteAllCoinEntities()

}