package com.example.alarmmanager.alarmutils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.example.alarmmanager.R
import com.example.alarmmanager.alarm.NotificationActivity
import com.example.alarmmanager.utils.AppConstants
import com.example.alarmmanager.utils.AppConstants.CHANNEL_ID
import com.example.alarmmanager.utils.Utils

class AlarmService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private val TAG = "AlarmService"

    override fun onCreate() {
        super.onCreate()
        Utils.logger(TAG, "Scheduling Alarm in Service")
        val alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(this, alarmTone)
        mediaPlayer.isLooping = true
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Utils.logger(TAG, "Inside on Start Command")
        val alertIntent = Intent(this, NotificationActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, alertIntent, 0)
        val alarmTitle = String.format("%s Alarm", intent.getStringExtra(AppConstants.ALARM_TITLE))
        val channelId = CHANNEL_ID

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = AppConstants.CHANNEL_NAME
            val descriptionText = AppConstants.CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        var action: NotificationCompat.Action =
                NotificationCompat.Action.Builder(0,
                        getString(R.string.dismiss), pendingIntent)
                        .build()

        val notification = NotificationCompat.Builder(this, channelId).apply {
            setContentTitle(alarmTitle)
            setContentText(getString(R.string.alarm_text))
            setSmallIcon(R.drawable.alarm_icon)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }.build()

        mediaPlayer.start()

        val pattern = longArrayOf(0, 100, 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createWaveform(pattern, 1)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(pattern, 0)
        }

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        vibrator.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}