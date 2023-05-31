package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.struct.Play

class PlayPageActivity : AppCompatActivity() {
    private lateinit var play : Play

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_page)
        val intent = intent
        play = intent.getSerializableExtra("play") as Play
        initPage()
    }

    private fun initPage(){
        val songName: TextView = findViewById(R.id.song_name)
        val author: TextView = findViewById(R.id.author)
        val goBack : Button = findViewById(R.id.go_back)
        songName.text = play.name
        author.text = play.author
        goBack.setOnClickListener {
            this.finish()
        }
    }
}