package com.example.alarmmanager.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.alarmmanager.data.Alarm

@Dao
interface AlarmDbDao {

    @Query("SELECT * FROM Alarm")
    fun getAllAlarms() : LiveData<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

}
