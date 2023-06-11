package com.example.androidmusicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.db_controll.FavourDbHelper
import com.example.androidmusicplayer.media.byteArrayToBitmap

class MyFavourActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_favour)
        initPage()
    }

    private fun initPage(){
        val goBack : Button = findViewById(R.id.go_back)
        val playAll : Button = findViewById(R.id.play_all)
        val cover : ImageView = findViewById(R.id.cover)
        goBack.setOnClickListener {
            this.finish()
        }
        val helper = FavourDbHelper(this)
        val playList = helper.query(null)
        val play = helper.getLast()
        if(play == null){
            cover.setImageResource(R.drawable.favour1)
        }
        else{
            if(play.bitmap != null)
                cover.setImageBitmap(byteArrayToBitmap(play.bitmap))
            else
                cover.setImageResource(R.drawable.favour1)
        }
        val recyclerView : RecyclerView = findViewById(R.id.play_list)
        val listAdapter = PlayAdapter(playList,this)
        recyclerView.adapter = listAdapter
    }
}