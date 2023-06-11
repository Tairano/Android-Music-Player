package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.db_controll.LocalPlayDbHelper

class ListActivity : AppCompatActivity() {
    private lateinit var queryName: String
    private lateinit var queryType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val intent = intent
        queryName = intent.getStringExtra("queryName").toString()
        queryType = intent.getStringExtra("queryType").toString()
        initPage()
    }

    private fun initPage(){
        val helper = LocalPlayDbHelper(this)
        val list = helper.queryByType(queryName, queryType)
        val topText : TextView = findViewById(R.id.top_text)
        val goBack : Button = findViewById(R.id.go_back)
        val playAll : Button = findViewById(R.id.play_all)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val adapter = PlayAdapter(list,this)
        topText.text = queryName
        recyclerView.adapter = adapter
        goBack.setOnClickListener {
            finish()
        }
        playAll.setOnClickListener {

        }
    }
}