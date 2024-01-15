package com.example.offlinefirstapp.data.remote.models

import com.example.offlinefirstapp.data.local.CoinEntity
import com.google.gson.annotations.SerializedName

data class CoinRemoteModel(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)

// Фукнция расширения, которая переводит  network model -> local model (модель сущности)
fun CoinRemoteModel.asEntity() = CoinEntity(
    id = id,
    isActive = isActive,
    name = name,
    rank = rank,
    symbol = symbol,
)

