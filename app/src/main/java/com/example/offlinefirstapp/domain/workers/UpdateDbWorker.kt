package com.example.offlinefirstapp.domain.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.offlinefirstapp.common.classes.UploadStatus
import com.example.offlinefirstapp.data.local.asExternalModel
import com.example.offlinefirstapp.data.remote.api.CoinPaprikaApi
import com.example.offlinefirstapp.data.remote.models.asEntity
import com.example.offlinefirstapp.data.repository.CoinRepository
import com.example.offlinefirstapp.domain.useCases.GetRemoteCoinsUseCase
import com.example.offlinefirstapp.domain.useCases.UpdateDBUseCase
import com.example.offlinefirstapp.presentation.screens.CoinListState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltWorker
class UpdateDbWorker @AssistedInject constructor(
    private val  coinPaprikaApi: CoinPaprikaApi,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val getRemoteCoinsUseCase: GetRemoteCoinsUseCase,
//    private val updateDBUseCase: UpdateDBUseCase,
    private val coinRepository: CoinRepository,

    ) : CoroutineWorker(context, workerParameters) {



    override suspend fun doWork(): Result {

        Log.d("WorkerInside", "The starting point of the worker")

        return try {

            /** Fetching remote coins*/

            val remoteCoins = coinRepository.getRemoteCoins().take(20)
//            val remoteCoins = coinRepository.getRemoteCoins().takeLast(20)
            val coinEntities = remoteCoins.map { coinRemoteModel ->
                coinRemoteModel.asEntity()
            }
            val coins = remoteCoins.map { coinRemoteModel ->
                coinRemoteModel.asEntity().asExternalModel()
            }







            /**Deleting all the coins*/
            withContext(Dispatchers.IO) {
                launch {
                    coinRepository.deleteAllCoinEntities()
                }.join()
            }

            /**Saving new coins in to the database*/
            withContext(Dispatchers.IO) {
                coinEntities.forEach { coinEntity ->
                    launch {
                        coinRepository.insertCoinEntity(coinEntity)
                    }
                }
            }


            Log.d("WorkerInside", "Work has successfully finished")
            Result.success()


        } catch (e: Exception) {
            Log.d("WorkerInside", "Error")
            Result.retry()
        }
    }




//    private fun getCoins(){
//        updateDBUseCase().onEach { uploadStatus ->
//            when(uploadStatus){
//                is UploadStatus.Error -> {
//                    _state.value = CoinListState().copy(
//                        error = uploadStatus.message ?: "An unexpected error occurred"
//                    )
//                }
//                is UploadStatus.Loading -> {
//                    _state.value = CoinListState(isLoading = true)
//                }
//                is UploadStatus.Success -> {
//                    _state.value = CoinListState(coins = uploadStatus.data ?: emptyList())
//                }
//
//                else -> {}
//            }
//
//        }.launchIn(viewModelScope)
//    }


//    override suspend fun doWork(): Result {
//
//        Log.d("WorkerInside", "The starting point of the worker")
//
//        return try {
//            updateDBUseCase().collect{ uploadStatus ->
//
//                if(uploadStatus == UploadStatus.Success){
//                    Result.success()
//                }
//
//
//                when(uploadStatus){
//                    is UploadStatus.Error -> {
//                        Log.d("WorkerInside", "Error")
//                        Result.retry() }
//                    is UploadStatus.Loading -> {}
//                    is UploadStatus.Success -> {
//                        Result.success()
//                        Log.d("WorkerInside", "Work has finished")
//                    }
//                }
//
//
//            }
//            Result.success()
//        } catch (e: Exception) {
//            Log.d("WorkerInside", "Error")
//            Result.retry()
//        }
//
//
//    }



//    override suspend fun doWork(): Result {
//        val succesVal: List<Int> = listOf(0,1,2,3)
//        Log.d("WorkerInside", "The starting point of the worker")
//
//        return try {
//            delay(50L)
//            Log.d("WorkerInside", "Work has finished")
//            Result.success()
//        } catch (e: Exception) {
//            Log.d("WorkerInside", "Error")
//            Result.retry()
//        }
//
//
//    }



}