package com.example.alarmmanager.alarmutils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.example.alarmmanager.utils.AppConstants
import com.example.alarmmanager.utils.Utils

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = "AlarmUtils"

    override fun onReceive(context: Context?, intent: Intent) {
        context?.let {
            if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
                Utils.logger(TAG, "Re-Scheduling Alarm")
                Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show()
                startRescheduleAlarmService(context)
            } else {
                Utils.logger(TAG, "Scheduling Alarm")
                Toast.makeText(context, "Schedule Alarm", Toast.LENGTH_SHORT).show()
                scheduleAlarmService(context, intent)
            }
        }
    }

    private fun scheduleAlarmService(context: Context, intent: Intent) {
        Utils.logger(TAG, "Scheduling Alarm Service")
        val title = intent.getStringExtra(AppConstants.ALARM_TITLE)
        val intentService = Intent(context, AlarmService::class.java).apply {
            putExtra(AppConstants.ALARM_TITLE, title)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    private fun startRescheduleAlarmService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}