package com.example.alarmmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alarmmanager.data.Alarm

@Database(entities = [Alarm::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun dao() : AlarmDbDao

}