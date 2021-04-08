package com.example.alarmmanager.utils

import android.widget.TimePicker

object TimeUtil {

    fun getTimePickerHour(tp: TimePicker) : Int {
        return tp.hour
    }

    fun getTimePickerMinutes(tp: TimePicker) : Int {
        return tp.minute
    }

}