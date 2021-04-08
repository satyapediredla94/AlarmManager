package com.example.alarmmanager.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alarmmanager.db.AlarmRepository
import com.example.alarmmanager.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class AlarmSchedulerViewModel @Inject constructor(
    private val alarmRepo: AlarmRepository
) : ViewModel() {

    val alarmTitle = MutableLiveData("")

    private val _alarmState = MutableLiveData<AlarmScheduleState>(AlarmScheduleState.Default)
    val alarmState : LiveData<AlarmScheduleState> = _alarmState

    fun schedule() {
        val title = alarmTitle.value
        if(title.isNullOrEmpty()) {
            Utils.logger("Alarm View Model", "Title is null or empty")
            _alarmState.value = AlarmScheduleState.ThrowTitleEmptyError
            return
        }
        _alarmState.value = AlarmScheduleState.ScheduleAlarm
    }

    fun scheduleAlarm(hours: Int, minutes: Int) {
        val title = alarmTitle.value
        Utils.logger("Alarm View Model", "Hours: $hours & Minutes: $minutes")
        Utils.logger("Alarm View Model", "Title : $title")
//        viewModelScope.launch {
//            val alarm = Alarm(
//                title = title!!,
//                hour = hours,
//                minute = minutes
//            )
//            alarmRepo.createAlarm(alarm)
//            _alarmState.postValue(AlarmScheduleState.AlarmScheduleSuccessful)
//        }
    }

    fun clearState() {
        _alarmState.value = AlarmScheduleState.Default
    }
}