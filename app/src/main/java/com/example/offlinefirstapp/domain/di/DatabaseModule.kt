package com.example.offlinefirstapp.domain.di

import android.app.Application
import androidx.room.Room
import com.example.offlinefirstapp.data.local.AppDatabase
import com.example.offlinefirstapp.data.local.repository.DataBaseRepositoryImpl
import com.example.offlinefirstapp.data.local.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app:Application) : AppDatabase{
        return  Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(db: AppDatabase): DatabaseRepository {
        return DataBaseRepositoryImpl(db.databaseDao)
    }





}