package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.utils.DBHelper

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initPage()
    }

    private fun initPage(){
        val helper = DBHelper(this)
        val playList = helper.getRecentPlayed()

        val goBack : Button = findViewById(R.id.go_back)
        val search : Button = findViewById(R.id.search)
        val inputText : EditText = findViewById(R.id.inputText)
        val recyclerView : RecyclerView = findViewById(R.id.searchResultView)
        val listAdapter = PlayAdapter(playList,this)
        recyclerView.adapter = listAdapter
        goBack.setOnClickListener { finish() }

        inputText.doAfterTextChanged {
            Toast.makeText(this, "此功能暂未实现，敬请期待。"+ inputText.text, Toast.LENGTH_SHORT).show()
        }
    }
}