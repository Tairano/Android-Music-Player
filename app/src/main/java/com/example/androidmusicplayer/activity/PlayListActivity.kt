package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.db_controll.LocalPlayDbHelper

class PlayListActivity : AppCompatActivity() {
    private lateinit var queryName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_list)
        val intent = intent
        queryName = intent.getSerializableExtra("queryName") as String
        initPlayList()
        initPage()
    }

    private fun initPage(){
        val listName: TextView = findViewById(R.id.name)
        val comment: TextView = findViewById(R.id.comment)
        val goBack : Button = findViewById(R.id.go_back)
//        listName.text = playList.name
//        comment.text = playList.comment
        goBack.setOnClickListener {
            this.finish()
        }
    }

    private fun initPlayList(){
        val helper = LocalPlayDbHelper(this)
//        val playList = helper.
        val recyclerView : RecyclerView = findViewById(R.id.play_list)
//        val listAdapter = PlayAdapter(playList,this)
//        recyclerView.adapter = listAdapter
    }
}