package com.example.offlinefirstapp.domain.useCases

import com.example.offlinefirstapp.common.classes.UploadStatus
import com.example.offlinefirstapp.data.local.asExternalModel
import com.example.offlinefirstapp.data.models.Coin
import com.example.offlinefirstapp.data.remote.models.asEntity
import com.example.offlinefirstapp.data.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class UpdateDBUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {


    operator fun invoke(): Flow<UploadStatus<List<Coin>>> = flow {
       try{
           emit(UploadStatus.Loading<List<Coin>>())
               val remoteCoins = coinRepository.getRemoteCoins().take(20)
                val coinEntities = remoteCoins.map { coinRemoteModel ->
                    coinRemoteModel.asEntity()
                }
           val coins = remoteCoins.map { coinRemoteModel ->
               coinRemoteModel.asEntity().asExternalModel()
           }

               withContext(Dispatchers.IO) {
//                   launch {
                    // val coroutineScope = CoroutineScope(coroutineContext)
                       coinEntities.forEach { coinEntity ->
                           launch {
                               coinRepository.insertCoinEntity(coinEntity)
                           }
                       }
//                   }.join()
//                   emit(UploadStatus.Success<List<Coin>>(coins))
               }
       } catch (e: retrofit2.HttpException){
           emit(UploadStatus.Error<List<Coin>>(message = e.localizedMessage ?: "An unexpected error occurred"))
       } catch (e: IOException){
           emit(UploadStatus.Error<List<Coin>>(message = "Couldn't reach the server. Check your internet connection"))
       }


    }


}