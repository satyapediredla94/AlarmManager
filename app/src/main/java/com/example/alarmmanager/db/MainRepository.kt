package com.example.alarmmanager.db

import androidx.lifecycle.LiveData
import com.example.alarmmanager.data.Alarm
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val alarmDbDao: AlarmDbDao
) : AlarmRepository {

    override fun getAllAlarms(): LiveData<List<Alarm>> = alarmDbDao.getAllAlarms()

    override suspend fun createAlarm(alarm: Alarm) = alarmDbDao.createAlarm(alarm)

    override suspend fun deleteAlarm(alarm: Alarm) = alarmDbDao.deleteAlarm(alarm)

}