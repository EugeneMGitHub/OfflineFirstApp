package com.example.offlinefirstapp.domain.workers

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.offlinefirstapp.common.classes.UploadStatus
import com.example.offlinefirstapp.data.models.Coin
import com.example.offlinefirstapp.data.remote.api.CoinPaprikaApi
import com.example.offlinefirstapp.data.repository.CoinRepository
import com.example.offlinefirstapp.domain.useCases.GetRemoteCoinsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject



@HiltWorker
class FetchCoinsWorker @AssistedInject constructor(
    private val  coinPaprikaApi: CoinPaprikaApi,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val getRemoteCoinsUseCase: GetRemoteCoinsUseCase,
//    private val updateDBUseCase: UpdateDBUseCase,
    private val coinRepository: CoinRepository,

    ) : CoroutineWorker(context, workerParameters) {

    var workResult = Result.failure()
    var fetchedCoins: List<Coin> = emptyList()


    private var state: String = ""
    private var state2 = mutableStateOf<String>("")
    var state3 by mutableStateOf("")
        private set


    override suspend fun doWork(): Result {

        getRemoteCoinsUseCase().collect{ uploadStatus ->
            when(uploadStatus){
                is UploadStatus.Error -> {}
                is UploadStatus.Loading -> {}
                is UploadStatus.Success -> {
                    workResult = Result.success()
                    fetchedCoins = uploadStatus.data ?: emptyList()
                    Result.success()
                }
            }
        }

        return workResult

    }

}
