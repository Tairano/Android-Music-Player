package com.example.androidmusicplayer.basicpage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.struct.Play
import com.example.androidmusicplayer.struct.PlayList
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.CustomAdapter
import com.example.androidmusicplayer.struct.PlayService

class Mine : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_my_main_page, container, false)
        initPlayList(layout)
        initButton(layout)
        return layout
    }

    private fun initPlayList(layout: View){
        val playList = ArrayList<PlayList>()
        var list = PlayList("我的喜欢", R.raw.logo)
        var play = Play("写给黄淮","邵帅")
        list.add(play)
        playList.add(list)
        list = PlayList("海边的少年", R.raw.logo)
        play = Play("春天里","汪峰")
        list.add(play)
        play = Play("少年","佚名")
        list.add(play)
        playList.add(list)
        list = PlayList("民谣", R.raw.logo)
        play = Play("董小姐","宋东野")
        list.add(play)
        play = Play("斑马，斑马","宋东野")
        list.add(play)
        play = Play("郭援朝","宋东野")
        list.add(play)
        playList.add(list)
        list = PlayList("摇滚", R.raw.logo)
        list.add(play)
        play = Play("活着","郝帅")
        list.add(play)
        play = Play("倔强","五月天")
        list.add(play)
        playList.add(list)
        val recyclerView : RecyclerView = layout.findViewById(R.id.my_play_list)
        val listAdapter = CustomAdapter(playList,this)
        recyclerView.adapter = listAdapter
    }

    private fun initButton(layout: View){
        val localMusic : Button = layout.findViewById(R.id.localMusic)
        val recentPlayed : Button = layout.findViewById(R.id.recentPlayed)
        val myCollection : Button = layout.findViewById(R.id.myCollection)
        val myFriends : Button = layout.findViewById(R.id.myFriends)
        val collectionAndSupport : Button = layout.findViewById(R.id.collectionAndSupport)
        val podcasts : Button = layout.findViewById(R.id.podcasts)
        localMusic.setOnClickListener {
            val intent = Intent("LocalMusicActivity")
            startActivity(intent)
        }
        recentPlayed.setOnClickListener {
            val intent = Intent("RecentPlayedActivity")
            startActivity(intent)
        }
        myCollection.setOnClickListener {
            val intent = Intent("MyCollectionActivity")
            startActivity(intent)
        }
        myFriends.setOnClickListener { notRealize() }
        collectionAndSupport.setOnClickListener { notRealize() }
        podcasts.setOnClickListener { notRealize() }
    }

    private fun notRealize(){
        Toast.makeText(activity, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
    }
}