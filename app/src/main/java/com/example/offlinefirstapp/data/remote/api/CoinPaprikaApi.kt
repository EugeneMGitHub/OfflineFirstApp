package com.example.offlinefirstapp.data.remote.api

import com.example.offlinefirstapp.data.remote.models.CoinRemoteModel

interface CoinPaprikaApi {
    suspend fun getRemoteCoins(): List<CoinRemoteModel>
}