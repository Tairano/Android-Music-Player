package com.example.androidmusicplayer.struct

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.androidmusicplayer.MainActivity
import com.example.androidmusicplayer.R

class PlayService : Service() {
    private lateinit var mediaPlayer : MediaPlayer

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        Log.d("play-service", "created..")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("play-service", "onStartCommand..")
        // 创建前台
        val channelId = "AndroidMusicPlayer.foreground"
        val channelName = "Android Music Player"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
        val intents = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(channelName)
            .setContentText("")
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.logo))
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        mediaPlayer = MediaPlayer()
        //        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
//        mediaPlayer.prepare()
//        mediaPlayer.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
        Log.d("play-service", "destory executed..")
    }
}