package com.example.androidmusicplayer.struct

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.androidmusicplayer.MainActivity
import com.example.androidmusicplayer.R

open class PlayService : Service() {
    private val binder = PlayBinder()
    private lateinit var player : MediaPlayer
    private lateinit var assetManager : AssetManager
    private lateinit var fd : AssetFileDescriptor
    private var path = ""
    private val playList = ArrayList<Play>()
    private var point = 0

    fun preSong(){
        point --
        if(point < 0)
            point = playList.size - 1
        playInList()
        addInRecentPlayed()
    }

    fun nextSong(){
        point ++
        if(point > playList.size - 1)
            point = 0
        playInList()
        addInRecentPlayed()
    }

    fun pauseOrPlay(){
        if(player.isPlaying)
            player.pause()
        else player.start()
    }

    fun addSong(play: Play){
        point = if(!playList.contains(play)){
            playList.add(play)
            playList.size -1
        } else {
            playList.indexOf(play)
        }
        playInList()
        addInRecentPlayed()
    }

    fun addInRecentPlayed(){
        Log.d("music","good")
    }

    fun playInList(){
        if(path != playList[point].path){
            path = playList[point].path
            player.stop()
            player.reset()
            setSource(path)
        }
        player.start()
        val intent = Intent("PlayPageActivity")
        intent.putExtra("titleName",playList[point].name)
        intent.putExtra("titleAuthor",playList[point].author)
    }

    fun setSource(filePath: String){
        fd = assetManager.openFd(filePath)
        player.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        player.prepare()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        assetManager = assets
        val service = Intent(this,PlayService::class.java)
        startService(service)
    }

    override fun onDestroy() {
        player.stop()
        player.release()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        player = MediaPlayer()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground(){
        // 创建前台
        val channelId = "AndroidMusicPlayer.foreground"
        val channelName = "Android Music Player"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
        val intents = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_IMMUTABLE)
        val view = RemoteViews(this.packageName, R.layout.foreground)

        val preButton = Intent(this, MyBroadcastReceiver::class.java)
        preButton.action = PRE_ACTION
        val pendingPreButton = PendingIntent.getBroadcast(this, 0, preButton, PendingIntent.FLAG_IMMUTABLE)

        val playButton = Intent(this, MyBroadcastReceiver::class.java)
        playButton.action = PLAY_ACTION
        val pendingPlayButton = PendingIntent.getBroadcast(this, 0, playButton, PendingIntent.FLAG_IMMUTABLE)

        val nextButton = Intent(this, MyBroadcastReceiver::class.java)
        nextButton.action = NEXT_ACTION
        val pendingNextButton = PendingIntent.getBroadcast(this, 0, nextButton, PendingIntent.FLAG_IMMUTABLE)

        view.setOnClickPendingIntent(R.id.pre_song,pendingPreButton)
        view.setOnClickPendingIntent(R.id.play,pendingPlayButton)
        view.setOnClickPendingIntent(R.id.next_song,pendingNextButton)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(channelName)
            .setContentText("")
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.logo))
            .setWhen(System.currentTimeMillis())
            .setContentIntent(pendingIntent)
            .setContent(view)
            .build()
        startForeground(1, notification)
    }

    inner class PlayBinder : Binder() {
        fun pre(){
            preSong()
        }

        fun next(){
            nextSong()
        }

        fun pause(){
            pauseOrPlay()
        }

        fun play(play: Play){
            addSong(play)
        }

        fun getName(): String = playList[point].name

        fun getAuthor(): String = playList[point].author

    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == PRE_ACTION) {
                preSong()
            }
            else if (action == PLAY_ACTION) {
                pauseOrPlay()
            }
            else if (action == NEXT_ACTION) {
                nextSong()
            }
        }
    }

    companion object {
        const val PRE_ACTION = "com.example.PRE_ACTION"
        const val PLAY_ACTION = "com.example.PLAY_ACTION"
        const val NEXT_ACTION = "com.example.NEXT_ACTION"
    }
}
