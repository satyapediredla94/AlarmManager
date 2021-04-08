package com.example.alarmmanager.schedule

sealed class AlarmScheduleState {

    object Default : AlarmScheduleState()

    object ScheduleAlarm : AlarmScheduleState()

    object AlarmScheduleSuccessful : AlarmScheduleState()

    object ThrowTitleEmptyError : AlarmScheduleState()

}
