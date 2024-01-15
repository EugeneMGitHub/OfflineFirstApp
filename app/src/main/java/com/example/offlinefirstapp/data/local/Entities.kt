package com.example.offlinefirstapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.offlinefirstapp.data.models.Coin

@Entity(tableName = "CoinEntity")
data class CoinEntity(
@PrimaryKey(autoGenerate = false)
val id: String,
val isActive: Boolean,
val name: String,
val rank: Int,
val symbol: String,
)


// Функция расширения, которая переводит local model (модель сущности) -> external model
fun CoinEntity.asExternalModel() = Coin(
    id = id,
    isActive = isActive,
    name = name,
    rank = rank,
    symbol = symbol,
)