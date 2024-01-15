package com.example.offlinefirstapp.data.repository

import com.example.offlinefirstapp.data.local.CoinEntity
import com.example.offlinefirstapp.data.local.repository.DatabaseRepository
import com.example.offlinefirstapp.data.remote.dataSources.CoinRemoteDataSource
import javax.inject.Inject
import com.example.offlinefirstapp.data.remote.models.CoinRemoteModel
import kotlinx.coroutines.flow.Flow


class CoinRepositoryImpl @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinDatabaseRepository: DatabaseRepository,
) : CoinRepository {


    override suspend fun insertCoinEntity(coinEntity: CoinEntity) {
       coinDatabaseRepository.insertCoinEntity(coinEntity)
    }

    override suspend fun getLocalCoins(): List<CoinEntity> {
        return coinDatabaseRepository.getAllCoinEntities()

    }

    override fun getLocalCoinsWithFlow(): Flow<List<CoinEntity>> {
        return coinDatabaseRepository.getAllCoinEntitiesFlow()
    }

    override suspend fun getRemoteCoins(): List<CoinRemoteModel> {
        return coinRemoteDataSource.getRemoteCoins()
    }

    override suspend fun deleteAllCoinEntities() {
        coinDatabaseRepository.deleteAllCoinEntities()
    }


    //    override suspend fun deleteAllCoinEntities() {
//        databaseDao.deleteAllCoinEntities()
//    }



    //    override suspend fun getCoins(): List<Coin> {
//
//        try{
////         Step 1: Try to fetch data from the local database
//            val localCoins = coinDatabaseRepository.getAllCoinEntities()
//
////             Step 2: If there is local data, return it
//            if (localCoins.isNotEmpty()) return localCoins.map { it.asExternalModel() }
//
//            // Step 3: If there is no local data, fetch data from the remote data source
//            val remoteCoins = coinRemoteDataSource.getRemoteCoins()
//            val remoteCoinsAsEntity = remoteCoins.forEach{
//                it.asEntity()
//            }
//
//            // Step 4: Save the remote data to the local database
//            remoteCoins.forEach {
//                coinDatabaseRepository.insertCoinEntity(it.asEntity())
//            }
//
//            remoteCoins.forEach {
//                coinDatabaseRepository.insertCoinEntity(it.asEntity())
//            }
//
//            // Step 5: Return the remote data
//            return remoteCoins.map { it.asEntity().asExternalModel() }
//
//
//        } catch (e: Exception) {
////            // Handle errors, such as database or network issues
////            // You might want to log the error or throw a custom exception
////            throw IOException("Error fetching coins: ${e.message}", e)
//        }
//
//
//        return emptyList()
//
//    }

}