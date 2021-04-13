package com.example.alarmmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmmanager.alarm.AlarmsViewModel
import com.example.alarmmanager.data.Alarm
import com.example.alarmmanager.databinding.AlarmItemBinding
import com.example.alarmmanager.utils.Utils

class AlarmAdapter(val viewmodel: AlarmsViewModel) : ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(AlarmDiffUtil()) {

    private val TAG = "AlarmAdapter"

    class AlarmViewHolder(private val binding: AlarmItemBinding,
                          private val viewmodel: AlarmsViewModel)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(alarm: Alarm) {
            binding.alarm = alarm
            binding.title.text = alarm.title
            val time = "${alarm.hour}:${alarm.minute}"
            binding.time.text = time
            binding.alarmEnabler.isChecked = alarm.isEnabled
            binding.viewmodel = viewmodel
        }

        companion object {
            fun from(parent: ViewGroup, viewmodel: AlarmsViewModel) : AlarmViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AlarmItemBinding.inflate(layoutInflater, parent, false)
                return AlarmViewHolder(binding, viewmodel)
            }
        }
    }

    class AlarmDiffUtil : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder.from(parent, viewmodel)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarmItem = getItem(position)
        holder.bind(alarmItem)
        holder.itemView.setOnClickListener {
            Utils.logger(TAG, "onBindViewHolder() : setOnClickListener() : clicked item at $position position")
            Utils.logger(TAG, "$alarmItem")
        }
    }

}