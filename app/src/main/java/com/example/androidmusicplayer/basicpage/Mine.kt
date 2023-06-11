package com.example.androidmusicplayer.basicpage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.androidmusicplayer.R

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
//        val helper = DBHelper(layout.context)
//        val playList = helper.getPlayLists()
//        val recyclerView : RecyclerView = layout.findViewById(R.id.my_play_list)
//        val listAdapter = CustomAdapter(playList,this)
//        recyclerView.adapter = listAdapter
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