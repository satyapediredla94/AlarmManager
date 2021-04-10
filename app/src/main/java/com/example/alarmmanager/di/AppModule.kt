package com.example.alarmmanager.di

import android.content.Context
import androidx.room.Room
import com.example.alarmmanager.db.AlarmDbDao
import com.example.alarmmanager.db.AlarmRepository
import com.example.alarmmanager.db.MainDatabase
import com.example.alarmmanager.db.MainRepository
import com.example.alarmmanager.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(value = [SingletonComponent::class])
object AppModule {

    @Provides
    @Singleton
    fun getDatabase(
            @ApplicationContext context: Context
    ) = Room.databaseBuilder(
            context,
            MainDatabase::class.java,
            AppConstants.DB_NAME
    ).build()

    @Provides
    @Singleton
    fun getDao(
            @ApplicationContext context: Context,
            database: MainDatabase
    ) = database.dao()

    @Provides
    @Singleton
    fun getMainRepo(
            alarmDbDao: AlarmDbDao
    ) = MainRepository(alarmDbDao) as AlarmRepository

}