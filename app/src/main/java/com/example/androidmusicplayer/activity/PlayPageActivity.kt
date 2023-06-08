package com.example.androidmusicplayer.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.struct.Play
import com.example.androidmusicplayer.struct.PlayService

val PLAY_TYPE_TOAST = arrayOf(
    0 to "单曲循环",
    1 to "顺序播放",
    2 to "随机播放"
)

class PlayPageActivity : AppCompatActivity() {
    private var play : Play? = null
    private lateinit var binder : PlayService.PlayBinder

    private lateinit var goBack : Button
    private lateinit var  songName : TextView
    private lateinit var  author : TextView
    private lateinit var  preSong : Button
    private lateinit var  nextSong : Button
    private lateinit var  playButton : Button
    private lateinit var  playType : Button
    private lateinit var  favour : Button

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as PlayService.PlayBinder
            binder.addAuthor(author)
            binder.addName(songName)
            binder.addPlay(playButton)
            binder.addTactic(playType)
            binder.addFavour(favour)
            play?.let { binder.play(it) }
            binder.refresh()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder.removeAuthor(author)
            binder.removeName(songName)
            binder.removePlay(playButton)
            binder.removeTactic(playType)
            binder.removeFavour(favour)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_page)
        val intent = intent
        play = intent.getSerializableExtra("play", Play::class.java)!!
        initPage()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initPage(){
        goBack = findViewById(R.id.go_back)
        songName = findViewById(R.id.song_name)
        author  = findViewById(R.id.author)
        preSong = findViewById(R.id.pre_song)
        nextSong = findViewById(R.id.next_song)
        playButton = findViewById(R.id.play)
        playType = findViewById(R.id.play_tactic)
        favour = findViewById(R.id.add_in_favour)

        goBack.setOnClickListener { this.finish() }
        preSong.setOnClickListener { binder.pre() }
        nextSong.setOnClickListener { binder.forceNext() }

        val intents = Intent(this, PlayService::class.java)
        bindService(intents, connection, Context.BIND_AUTO_CREATE)
    }
}