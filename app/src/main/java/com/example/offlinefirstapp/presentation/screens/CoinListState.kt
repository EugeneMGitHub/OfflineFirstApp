package com.example.offlinefirstapp.presentation.screens

import com.example.offlinefirstapp.data.models.Coin
import java.lang.Error

data class CoinListState (
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)