package com.example.offlinefirstapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        CoinEntity::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract val databaseDao: DatabaseDao
}

