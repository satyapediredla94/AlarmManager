package com.example.alarmmanager.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmmanager.R
import com.example.alarmmanager.alarmutils.AlarmReceiver
import com.example.alarmmanager.utils.AppConstants
import com.example.alarmmanager.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    private lateinit var dimiss: Button
    private val TAG = "NotificationActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        dimiss = findViewById(R.id.dismiss)
        dimiss.setOnClickListener {
            Utils.logger(TAG, "Dismissed")
            val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmId = intent.getIntExtra(AppConstants.ALARM_ID, 0)
            val alarmIntent = Intent(this, AlarmReceiver::class.java).let {
                PendingIntent.getBroadcast(this, alarmId, it, PendingIntent.FLAG_ONE_SHOT)
            }
            alarmMgr.cancel(alarmIntent)
        }
    }

}