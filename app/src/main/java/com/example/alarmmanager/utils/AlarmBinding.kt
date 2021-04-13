package com.example.alarmmanager.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmmanager.adapter.AlarmAdapter
import com.example.alarmmanager.data.Alarm

@BindingAdapter("app:setItems")
fun setItems(alarms: RecyclerView, alarmList: List<Alarm>?) {

    alarmList?.let {
        (alarms.adapter as AlarmAdapter).submitList(it)
    }

}
