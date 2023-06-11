package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.db_controll.LocalPlayDbHelper

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initPage()
    }

    private fun initPage(){
        val helper = LocalPlayDbHelper(this)
        val goBack : Button = findViewById(R.id.go_back)
        val inputText : EditText = findViewById(R.id.inputText)
        goBack.setOnClickListener { finish() }
        var playList = helper.query(null)
        var recyclerView : RecyclerView = findViewById(R.id.searchResultView)
        var listAdapter = PlayAdapter(playList,this)
        recyclerView.adapter = listAdapter
        inputText.doAfterTextChanged {
            playList = helper.query(inputText.text.toString())
            recyclerView = findViewById(R.id.searchResultView)
            listAdapter = PlayAdapter(playList,this)
            recyclerView.adapter = listAdapter
        }
    }
}