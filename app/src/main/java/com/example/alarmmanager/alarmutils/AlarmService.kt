package com.example.alarmmanager.alarmutils

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

class AlarmService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator

    override fun onCreate() {
        super.onCreate()
        val alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer.create(this, alarmTone)
        mediaPlayer.isLooping = true
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notifIntent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, flags)
        val alarmTitle = String.format("%s Alarm", intent!!.getStringExtra(AppConstants.ALARM_TITLE))
        val channelId = AppConstants.CHANNEL_ID
        val notification = NotificationCompat.Builder(this, channelId).apply {
            setContentTitle(alarmTitle)
            setContentText(getString(R.string.alarm_text))
            setSmallIcon(R.drawable.alarm_icon)
            setContentIntent(pendingIntent)
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

        return START_STICKY
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