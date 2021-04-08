package com.example.alarmmanager.alarmutils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.example.alarmmanager.utils.AppConstants

class AlarmUtils : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        context?.let {
            if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
                Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show()
                startRescheduleAlarmService(context)
            } else {
                Toast.makeText(context, "Schedule Alarm", Toast.LENGTH_SHORT).show()
                scheduleAlarmService(context, intent)
            }
        }
    }

    private fun scheduleAlarmService(context: Context, intent: Intent) {
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