package com.example.offlinefirstapp.data.remote.api

import com.example.offlinefirstapp.common.constants.Constants.COINS_END_POINT
import com.example.offlinefirstapp.data.remote.models.CoinRemoteModel
import retrofit2.http.GET

interface RetrofitCoinPaprikaApi : CoinPaprikaApi {

    @GET(COINS_END_POINT)
    override suspend fun getRemoteCoins(): List<CoinRemoteModel>

}