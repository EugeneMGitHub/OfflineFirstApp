package com.example.offlinefirstapp.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.workDataOf
import com.example.offlinefirstapp.common.classes.UploadStatus
import com.example.offlinefirstapp.data.local.repository.DatabaseRepository
import com.example.offlinefirstapp.data.repository.CoinRepository
import com.example.offlinefirstapp.domain.useCases.UpdateDBUseCase
import com.example.offlinefirstapp.domain.workers.UpdateDbWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainScreenViewModel
@Inject constructor(
    private val updateDBUseCase: UpdateDBUseCase,
    private val coinRepository: CoinRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel(){

    private val _state  = MutableStateFlow<CoinListState>(CoinListState())
    val state = _state.asStateFlow()

//    val coinsFromDbWithFlow = databaseRepository.getAllCoinEntitiesFlow()
    val coinsFromDbWithFlow = coinRepository.getLocalCoinsWithFlow()


//    private val workManager = WorkManager.getInstance()


    init{
//        viewModelScope.launch {  deleteAllCoinEntities() }
//        getCoins()
//        startWorkManager()
    }


    private fun getCoins(){
        updateDBUseCase().onEach { uploadStatus ->
            when(uploadStatus){
                is UploadStatus.Error -> {
                    _state.value = CoinListState().copy(
                        error = uploadStatus.message ?: "An unexpected error occurred"
                    )
                }
                is UploadStatus.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
                is UploadStatus.Success -> {
                    _state.value = CoinListState(coins = uploadStatus.data ?: emptyList())
                }

                else -> {}
            }

        }.launchIn(viewModelScope)
    }


     suspend fun deleteAllCoinEntities(){
            databaseRepository.deleteAllCoinEntities()
    }


}