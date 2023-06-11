package com.example.androidmusicplayer.media

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.androidmusicplayer.MainActivity
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.activity.PLAY_TYPE_TOAST
import com.example.androidmusicplayer.struct.MyListener
import com.example.androidmusicplayer.struct.Play
import kotlin.random.Random

fun rand(max: Int, min: Int): Int = Random.nextInt(max - min + 1) + min

open class PlayService : Service() {
    private val binder = PlayBinder()
    private lateinit var player : MediaPlayer
    private var path = ""
    private val playList = ArrayList<Play>()
    private var point = 0
    private val listener = MyListener()

    private fun changeSongIndex(){
        if(listener.playTypeStatus == 0){
            return
        }
        else if(listener.playTypeStatus == 1){
            point ++
            if(point > playList.size - 1)
                point = 0
        }
        else{
            point = if(playList.size > 1)
                rand(playList.size - 1, 0)
            else
                0
        }
    }

    fun preSong(){
        if(listener.playTypeStatus == 2){
            changeSongIndex()
            playInList()
        }
        else{
            point --
            if(point < 0)
                point = playList.size - 1
            playInList()
        }

    }

    fun nextSong(){
        listener.refresh()
        changeSongIndex()
        playInList()
    }

    fun forceNextSong(){
        if(listener.playTypeStatus == 0){
            point --
            if(point < 0)
                point = playList.size - 1
            playInList()
        }
        else nextSong()
    }

    fun pauseOrPlay(){
        listener.playStatus = if(player.isPlaying) {
            player.pause()
            1
        } else{
            player.start()
            0
        }
        listener.refresh()
    }

    fun addSong(play: Play?){
        if(play != null){
            point = if(!playList.contains(play)){
                playList.add(play)
                playList.size -1
            } else {
                playList.indexOf(play)
            }
            playInList()
        }
        else Toast.makeText(this, "未找到文件，请重新读取本地歌曲。", Toast.LENGTH_SHORT).show()
    }

    private fun playInList(){
        if(playList.size == 0)
            return
        if(path != playList[point].path){
            path = playList[point].path.toString()
            player.stop()
            player.reset()
            setSource(path)
        }
        listener.playStatus = 0
        listener.name = playList[point].title
        listener.author = playList[point].artist.toString()
        listener.refresh()
        player.start()
    }

    private fun setSource(filePath: String){
        player.setDataSource(filePath)
        player.prepare()
    }

    fun changePlayType(){
        listener.playTypeStatus = (listener.playTypeStatus + 1) % 3
        listener.refresh()
    }

    fun changeFavourStatus(){
        listener.refresh()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
        startForeground()
        player.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            nextSong()
        })
        val service = Intent(this,PlayService::class.java)
        startService(service)
    }

    override fun onDestroy() {
        player.stop()
        player.release()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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

//        val preButton = Intent(this, MyBroadcastReceiver::class.java)
//        preButton.action = PRE_ACTION
//        val pendingPreButton = PendingIntent.getBroadcast(this, 0, preButton, PendingIntent.FLAG_IMMUTABLE)
//
//        val playButton = Intent(this, MyBroadcastReceiver::class.java)
//        playButton.action = PLAY_ACTION
//        val pendingPlayButton = PendingIntent.getBroadcast(this, 0, playButton, PendingIntent.FLAG_IMMUTABLE)
//
//        val nextButton = Intent(this, MyBroadcastReceiver::class.java)
//        nextButton.action = NEXT_ACTION
//        val pendingNextButton = PendingIntent.getBroadcast(this, 0, nextButton, PendingIntent.FLAG_IMMUTABLE)
//
//        view.setOnClickPendingIntent(R.id.pre_song,pendingPreButton)
//        view.setOnClickPendingIntent(R.id.play,pendingPlayButton)
//        view.setOnClickPendingIntent(R.id.next_song,pendingNextButton)

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

        fun play(play: Play?){
            addSong(play)
        }

        fun changePlay(){
            changePlayType()
        }

        fun changeFavour(){
            changeFavourStatus()
        }

        fun forceNext(){
            forceNextSong()
        }

        fun getListener(): MyListener = listener

        fun getPlayer(): MediaPlayer = player

        fun addPlay(button: Button){
            listener.addPlayButton(button)
            button.setOnClickListener { pause() }
        }

        fun removePlay(button: Button){
            listener.removePlayButton(button)
        }

        fun addTactic(button: Button){
            listener.addPlayTypeButton(button)
            button.setOnClickListener {
                changePlay()
                Toast.makeText(baseContext, "切换到" + PLAY_TYPE_TOAST[binder.getListener().playTypeStatus].second , Toast.LENGTH_SHORT).show()
            }
        }

        fun removeTactic(button: Button){
            listener.removePlayTypeButton(button)
        }

        fun addFavour(button: Button){
            listener.addFavourButton(button)
            button.setOnClickListener { changeFavour() }
        }

        fun removeFavour(button: Button){
            listener.removeFavourButton(button)
        }

        fun addName(view: TextView){
            listener.addNameView(view)
        }

        fun removeName(view: TextView){
            listener.removeNameView(view)
        }

        fun addAuthor(view: TextView){
            listener.addAuthorView(view)
        }

        fun removeAuthor(view: TextView){
            listener.removeAuthorView(view)
        }

        fun refresh() = listener.refresh()
    }
}
