package ru.mirea.istornikov.mireaproject.ui.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import ru.mirea.istornikov.mireaproject.R


class MusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.motn)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer.stop()
    }
}