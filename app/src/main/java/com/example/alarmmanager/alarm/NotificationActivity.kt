package com.example.alarmmanager.alarm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmmanager.MainActivity
import com.example.alarmmanager.R
import com.example.alarmmanager.alarmutils.AlarmService
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
            val intentService = Intent(this.applicationContext, AlarmService::class.java)
            this.stopService(intentService)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}