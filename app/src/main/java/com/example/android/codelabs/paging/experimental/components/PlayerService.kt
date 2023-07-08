package com.example.android.codelabs.paging.experimental.components

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings

class PlayerService : Service() {

    private lateinit var player: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ServiceManager.isStarted = true
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        player.isLooping = true
        player.start()
        return START_STICKY
    }

    override fun onDestroy() {
        ServiceManager.isStarted = false
        super.onDestroy()
        player.stop()
        player.release()
    }

    // используется для bound service
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}