package com.example.alarmmanager.alarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarmmanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
    }

}