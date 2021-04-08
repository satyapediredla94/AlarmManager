package com.example.alarmmanager.db

import androidx.lifecycle.LiveData
import com.example.alarmmanager.data.Alarm

interface AlarmRepository {

    fun getAllAlarms() : LiveData<List<Alarm>>

    suspend fun createAlarm(alarm: Alarm)

    suspend fun deleteAlarm(alarm: Alarm)

}