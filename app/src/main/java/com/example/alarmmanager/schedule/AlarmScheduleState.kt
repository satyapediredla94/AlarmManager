package com.example.alarmmanager.schedule

import com.example.alarmmanager.data.Alarm

sealed class AlarmScheduleState {

    object Default : AlarmScheduleState()

    object ScheduleAlarm : AlarmScheduleState()

    data class AlarmScheduleSuccessful(val alarm: Alarm) : AlarmScheduleState()

    object ThrowTitleEmptyError : AlarmScheduleState()

}
