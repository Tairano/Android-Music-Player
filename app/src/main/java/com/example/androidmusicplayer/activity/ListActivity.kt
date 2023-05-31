package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.struct.BasicList

class ListActivity : AppCompatActivity() {
    private lateinit var list : BasicList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val intent = intent
        list = intent.getSerializableExtra("list") as BasicList
        initPage()
    }

    private fun initPage(){
        val topText : TextView = findViewById(R.id.top_text)
        val goBack : Button = findViewById(R.id.go_back)
        val playAll : Button = findViewById(R.id.play_all)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val adapter = PlayAdapter(list.getList(),this)
        topText.text = list.str
        recyclerView.adapter = adapter
        goBack.setOnClickListener {
            finish()
        }
        playAll.setOnClickListener {

        }
    }
}