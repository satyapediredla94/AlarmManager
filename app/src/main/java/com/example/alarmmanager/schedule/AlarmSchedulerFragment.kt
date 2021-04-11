package com.example.alarmmanager.schedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alarmmanager.R
import com.example.alarmmanager.alarmutils.AlarmReceiver
import com.example.alarmmanager.databinding.FragmentAlarmSchedulerBinding
import com.example.alarmmanager.utils.AppConstants
import com.example.alarmmanager.utils.TimeUtil
import com.example.alarmmanager.utils.Utils
import java.util.*


class AlarmSchedulerFragment : Fragment() {

    private lateinit var alarmSchedulerViewModel: AlarmSchedulerViewModel
    private lateinit var binding: FragmentAlarmSchedulerBinding
    private val TAG = "AlarmSchedulerFragment"
    private var title = ""
    private var hours = 0
    private var minutes = 0

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
                scheduleAlarm(title)
                navigateToAllAlarms()
            }
            is AlarmScheduleState.ScheduleAlarm -> {
                clearState()
                hours = TimeUtil.getTimePickerHour(binding.timePicker)
                minutes = TimeUtil.getTimePickerMinutes(binding.timePicker)
                Utils.logger(TAG, "Time picker hours: $hours & Minutes: $minutes")
                alarmSchedulerViewModel.scheduleAlarm(hours, minutes)
                title = binding.titleEt.text.toString()
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

    private fun scheduleAlarm(title: String) {
        Utils.logger(TAG, "In")
        val alarmMgr = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmId = AppConstants.ALARM_ID_INT
        val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java).let { intent ->
            Utils.logger(TAG, "Schedule an Alarm inside intent with title : $title")
//            intent.putExtra(AppConstants.ALARM_TITLE, title)
            intent.putExtra(AppConstants.ALARM_ID, alarmId)
            PendingIntent.getBroadcast(requireContext(), alarmId, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        alarmMgr.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        )
    }

    private fun navigateToAllAlarms() {
        return
    }

    private fun clearState() {
        alarmSchedulerViewModel.clearState()
    }

}