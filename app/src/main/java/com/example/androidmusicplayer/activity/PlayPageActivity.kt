package com.example.androidmusicplayer.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.struct.Play
import com.example.androidmusicplayer.struct.PlayService

class PlayPageActivity : AppCompatActivity() {
    private var playing = true
    private lateinit var play : Play
    private lateinit var binder : PlayService.PlayBinder
    private lateinit var songName: TextView
    private lateinit var author: TextView

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as PlayService.PlayBinder
            binder.play(play)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_page)
        val intent = intent
        play = intent.getSerializableExtra("play") as Play
        initPage()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initPage(){
        val goBack : Button = findViewById(R.id.go_back)
        songName = findViewById(R.id.song_name)
        author = findViewById(R.id.author)

        songName.text = play.name
        author.text = play.author
        goBack.setOnClickListener {
            this.finish()
        }
        val intents = Intent(this, PlayService::class.java)
        bindService(intents, connection, Context.BIND_AUTO_CREATE)

        val preSong : Button = findViewById(R.id.pre_song)
        val nextSong : Button = findViewById(R.id.next_song)
        val playButton : Button = findViewById(R.id.play)
        preSong.setOnClickListener {
            binder.pre()
            resetTitle()
        }
        playButton.setOnClickListener {
            binder.pause()
            if(playing){
                playButton.setBackgroundResource(R.drawable.stop)
                playing = false
            }
            else {
                playButton.setBackgroundResource(R.drawable.play)
                playing = true
            }
        }
        nextSong.setOnClickListener {
            binder.next()
            resetTitle()
        }
    }

    private fun resetTitle(){
        songName.text = binder.getName()
        author.text = binder.getAuthor()
    }
}