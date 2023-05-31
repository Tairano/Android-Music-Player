package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.struct.PlayList

class PlayListActivity : AppCompatActivity() {
    private lateinit var playList : PlayList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_list)
        val intent = intent
        playList = intent.getSerializableExtra("list") as PlayList
        initPlayList()
        initPage()
    }

    private fun initPage(){
        val listName: TextView = findViewById(R.id.list_name)
        val comment: TextView = findViewById(R.id.comment)
        val createTime: TextView = findViewById(R.id.create_time)
        val goBack : Button = findViewById(R.id.go_back)
        listName.text = playList.name
        comment.text = playList.comment
        createTime.text = playList.timeStamp
        goBack.setOnClickListener {
            this.finish()
        }
    }

    private fun initPlayList(){
        val recyclerView : RecyclerView = findViewById(R.id.play_list)
        val listAdapter = PlayAdapter(playList.getList(),this)
        recyclerView.adapter = listAdapter
    }
}