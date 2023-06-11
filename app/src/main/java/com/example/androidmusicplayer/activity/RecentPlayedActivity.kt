package com.example.androidmusicplayer.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.struct.Play

class RecentPlayedActivity : AppCompatActivity() {
//    private lateinit var binder : PlayService.PlayBinder
    private lateinit var playList : ArrayList<Play>
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            binder = service as PlayService.PlayBinder
            initPlayList()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_played)
        val goBack : Button = findViewById(R.id.go_back)
        goBack.setOnClickListener { finish() }
//        val intents = Intent(this, PlayService::class.java)
//        bindService(intents, connection, Context.BIND_AUTO_CREATE)
    }

    private fun initPlayList(){
//        playList = binder.getRecentPlayed()
        val recyclerView : RecyclerView = findViewById(R.id.recentPlayedRecycleView)
        val listAdapter = PlayAdapter(playList,this)
        recyclerView.adapter = listAdapter
    }
}