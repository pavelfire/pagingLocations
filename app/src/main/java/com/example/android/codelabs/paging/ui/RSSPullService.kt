package com.example.android.codelabs.paging.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.android.codelabs.paging.R

const val CHANNEL_ID = "Main Channel RSS Pull Service"

class RSSPullService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_star)
            .setContentTitle("Pavel Activity Intents notification")
            .setContentText("Yes, now service foreground running")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Service foreground running you can stop then," +
                        " by pressing stop service button in the app"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        startForeground(1, builder)
        Log.d("RSSPullService", "onStartCommand")
        return START_STICKY

    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d("RSSPullService", "stopService")
        stopSelf()
        return super.stopService(name)
    }

    override fun startForegroundService(service: Intent?): ComponentName? {
        return super.startForegroundService(service)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}