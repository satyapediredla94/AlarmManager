package com.example.alarmmanager.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmmanager.data.Alarm
import com.example.alarmmanager.db.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "AlarmsViewModel"

@HiltViewModel
class AlarmsViewModel @Inject constructor(
        private val alarmRepository: AlarmRepository
) : ViewModel() {

    private val _alarms = MutableLiveData<List<Alarm>>()
    var alarms : LiveData<List<Alarm>> = _alarms

    val isEnabled = MutableLiveData(false)

    init {
       alarms = alarmRepository.getAllAlarms()
    }

    fun updateEnabler(alarm: Alarm) {
        val enabled = isEnabled.value
        enabled?.let {
            if(alarm.isEnabled) {
                val newAlarm = Alarm(
                        title = alarm.title,
                        hour = alarm.hour,
                        minute = alarm.minute,
                        isEnabled = !alarm.isEnabled,
                        id = alarm.id
                )
                viewModelScope.launch {
                    alarmRepository.createAlarm(newAlarm)
                    alarms = alarmRepository.getAllAlarms()
                }
            } else {
                val newAlarm = Alarm(
                        title = alarm.title,
                        hour = alarm.hour,
                        minute = alarm.minute,
                        isEnabled = !alarm.isEnabled,
                        id = alarm.id
                )
                viewModelScope.launch {
                    alarmRepository.createAlarm(newAlarm)
                    alarms = alarmRepository.getAllAlarms()
                }
            }
        }

        }

    fun deleteAlarm(alarm: Alarm) {
        viewModelScope.launch {
            alarmRepository.deleteAlarm(alarm)
        }
    }


}