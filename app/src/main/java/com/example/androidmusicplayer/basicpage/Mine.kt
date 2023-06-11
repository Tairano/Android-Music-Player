package com.example.androidmusicplayer.basicpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayListAdapter
import com.example.androidmusicplayer.db_controll.FavourDbHelper
import com.example.androidmusicplayer.db_controll.ListDbHelper
import com.example.androidmusicplayer.media.byteArrayToBitmap

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

    @SuppressLint("SetTextI18n")
    private fun initPlayList(layout: View){
        val favourBitmap : ImageView = layout.findViewById(R.id.favour_image)
        val info : TextView = layout.findViewById(R.id.favour_info)
        val favourHelper = FavourDbHelper(layout.context)
        info.text = favourHelper.query(null).size.toString() + "首"
        val play = favourHelper.getLast()
        if(play == null){
            favourBitmap.setImageResource(R.drawable.favour1)
        }
        else{
            if(play.bitmap != null)
                favourBitmap.setImageBitmap(byteArrayToBitmap(play.bitmap))
            else
                favourBitmap.setImageResource(R.drawable.favour1)
        }
        val helper = ListDbHelper(layout.context)
        val playList = helper.query(null)
        val recyclerView : RecyclerView = layout.findViewById(R.id.my_play_list)
        val listAdapter = PlayListAdapter(playList,this)
        recyclerView.adapter = listAdapter
    }

    private fun initButton(layout: View){
        val localMusic : LinearLayout = layout.findViewById(R.id.localMusic)
        val recentPlayed : LinearLayout = layout.findViewById(R.id.recentPlayed)
        val myCollection : LinearLayout = layout.findViewById(R.id.myCollection)
        val myFriends : LinearLayout = layout.findViewById(R.id.myFriends)
        val collectionAndSupport : LinearLayout = layout.findViewById(R.id.collectionAndSupport)
        val podcasts : LinearLayout = layout.findViewById(R.id.podcasts)
        val myFavour : LinearLayout = layout.findViewById(R.id.favour)
        localMusic.setOnClickListener {
            val intent = Intent("LocalMusicActivity")
            startActivity(intent)
        }
        recentPlayed.setOnClickListener {
            val intent = Intent("RecentPlayedActivity")
            startActivity(intent)
        }
        myFavour.setOnClickListener {
            val intent = Intent("MyFavourActivity")
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