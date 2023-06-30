package com.example.androidmusicplayer.media

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.Button
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.androidmusicplayer.MainActivity
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.activity.PLAY_TYPE_TOAST
import com.example.androidmusicplayer.db_controll.FavourDbHelper
import com.example.androidmusicplayer.struct.MyListener
import com.example.androidmusicplayer.struct.Play
import kotlin.random.Random

fun rand(max: Int, min: Int): Int = Random.nextInt(max - min + 1) + min

open class PlayService : Service() {
    private val receiver = MyBroadcastReceiver()
    private lateinit var view : RemoteViews
    private val helper = FavourDbHelper(this)
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
        startForeground()
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
        listener.favourStatus = helper.getStatus(playList[point].title)
        if(byteArrayToBitmap(playList[point].bitmap) != null)
            listener.bitmap = byteArrayToBitmap(playList[point].bitmap)!!
        else
            listener.bitmap = drawableToBitmap(this, R.drawable.album)
        listener.refresh()
        startForeground()
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
        listener.favourStatus = helper.insertOrRemove(playList[point])
        listener.refresh()
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.android.music.player.foreground.NEXT")
        intentFilter.addAction("com.android.music.player.foreground.PRE")
        intentFilter.addAction("com.android.music.player.foreground.PAUSE")
        registerReceiver(receiver, intentFilter)
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
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("RemoteViewLayout")
    private fun startForeground(){
        // 创建前台
        val channelId = "AndroidMusicPlayer.foreground"
        val channelName = "Android Music Player"
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
        val intents = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intents, PendingIntent.FLAG_IMMUTABLE)
        view = RemoteViews(this.packageName, R.layout.foreground)
        if(playList.size > 0){
            view.setImageViewBitmap(R.id.bitmap,byteArrayToBitmap(playList[point].bitmap))
            view.setTextViewText(R.id.name,playList[point].title)
            view.setTextViewText(R.id.author,playList[point].artist)
        }
        else{
            view.setImageViewResource(R.id.bitmap,R.drawable.album)
            view.setTextViewText(R.id.name,"无歌曲播放")
            view.setTextViewText(R.id.author,"-")
        }
        val intentNext = Intent(this, MyBroadcastReceiver::class.java)
        intentNext.action = "com.android.music.player.foreground.NEXT"
        val pendingIntent1 = PendingIntent.getBroadcast(this, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT)

        val intentPause = Intent(this, MyBroadcastReceiver::class.java)
        intentPause.action = "com.android.music.player.foreground.PAUSE"
        val pendingIntent2 = PendingIntent.getBroadcast(this, 0, intentPause, PendingIntent.FLAG_UPDATE_CURRENT)

        val intentPre = Intent(this, MyBroadcastReceiver::class.java)
        intentPre.action = "com.android.music.player.foreground.PRE"
        val pendingIntent3 = PendingIntent.getBroadcast(this, 0, intentPre, PendingIntent.FLAG_UPDATE_CURRENT)

        view.setOnClickPendingIntent(R.id.next_song,pendingIntent1)
        view.setOnClickPendingIntent(R.id.play,pendingIntent2)
        view.setOnClickPendingIntent(R.id.pre_song,pendingIntent3)

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

        fun addBitmap(view: ImageView){
            listener.addBitmapView(view)
        }

        fun removeBitmap(view: ImageView){
            listener.removeBitmapView(view)
        }

        fun removeAuthor(view: TextView){
            listener.removeAuthorView(view)
        }

        fun refresh() = listener.refresh()
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "com.android.music.player.foreground.NEXT"->{
                    nextSong()
                }
                "com.android.music.player.foreground.PRE"->{
                    preSong()
                }
                "com.android.music.player.foreground.PAUSE"->{
                    pauseOrPlay()
                }
            }
            refreshForeground()
        }
    }

    fun refreshForeground(){
        startForeground()
    }
}
