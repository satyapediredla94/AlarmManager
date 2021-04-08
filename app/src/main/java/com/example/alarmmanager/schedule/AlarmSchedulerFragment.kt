package com.example.alarmmanager.schedule

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alarmmanager.R
import com.example.alarmmanager.databinding.FragmentAlarmSchedulerBinding
import com.example.alarmmanager.utils.TimeUtil
import com.example.alarmmanager.utils.Utils

class AlarmSchedulerFragment : Fragment() {

    private lateinit var alarmSchedulerViewModel: AlarmSchedulerViewModel
    private lateinit var binding: FragmentAlarmSchedulerBinding
    private val TAG = "AlarmSchedulerFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmSchedulerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarmSchedulerViewModel = ViewModelProvider(requireActivity()).get(AlarmSchedulerViewModel::class.java)
        binding.viewmodel = alarmSchedulerViewModel
        alarmSchedulerViewModel.alarmState.observe(requireActivity(), { observe(it) })
    }

    private fun observe(alarmState: AlarmScheduleState) {
        when(alarmState){
            is AlarmScheduleState.AlarmScheduleSuccessful -> {
                scheduleAlarm()
                navigateToAllAlarms()
            }
            is AlarmScheduleState.ScheduleAlarm -> {
                clearState()
                val hours = TimeUtil.getTimePickerHour(binding.timePicker)
                val minutes = TimeUtil.getTimePickerMinutes(binding.timePicker)
                Utils.logger(TAG, "Time picker hours: $hours & Minutes: $minutes")
                alarmSchedulerViewModel.scheduleAlarm(hours, minutes)
                binding.titleEt.setText("")
            }
            is AlarmScheduleState.ThrowTitleEmptyError -> {
                clearState()
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.empty_title)
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
            else -> {}
        }
    }

    private fun scheduleAlarm() {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

    }

    private fun navigateToAllAlarms() {
        TODO("Not yet implemented")
    }

    private fun clearState() {
        alarmSchedulerViewModel.clearState()
    }

}