package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.db_controll.PlayDbHelper
import com.example.androidmusicplayer.media.byteArrayToBitmap
import com.example.androidmusicplayer.struct.PlayList

class PlayListActivity : AppCompatActivity() {
    private lateinit var list : PlayList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_list)
        val intent = intent
        list = intent.getSerializableExtra("list") as PlayList
        initPlayList()
        initPage()
    }

    private fun initPage(){
        val listName: TextView = findViewById(R.id.name)
        val comment: TextView = findViewById(R.id.comment)
        val goBack : Button = findViewById(R.id.go_back)
        val bitmap: ImageView = findViewById(R.id.cover)
        listName.text = list.name
        comment.text = list.comment
        if(list.bitmap != null){
            bitmap.setImageBitmap(byteArrayToBitmap(list.bitmap))
        }
        else{
            bitmap.setImageResource(R.drawable.album)
        }
        goBack.setOnClickListener {
            this.finish()
        }
    }

    private fun initPlayList(){
        val helper = PlayDbHelper(this)
        Log.d("sssss",helper.queryByListId(list.id).size.toString())
        val playList = helper.queryByListId(list.id)
        val recyclerView : RecyclerView = findViewById(R.id.play_list)
        val listAdapter = PlayAdapter(playList,this)
        recyclerView.adapter = listAdapter
    }
}