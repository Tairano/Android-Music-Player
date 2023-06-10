package com.example.androidmusicplayer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.BasicAdapter
import com.example.androidmusicplayer.struct.Album
import com.example.androidmusicplayer.struct.BasicList
import com.example.androidmusicplayer.struct.Play

class AlbumFragment : Fragment() {
    private lateinit var layout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_folder, container, false)
        initPage()
        return layout
    }

    private fun initPage(){
        val albumList = ArrayList<BasicList>()
        var list = Album("写给黄淮","邵帅","2019.1.3")
        var play = Play("写给黄淮","邵帅")
        list.add(play)
        albumList.add(list)
        list = Album("写给黄淮","邵帅", "2019.1.3")
        play = Play("写给黄淮","邵帅")
        list.add(play)
        albumList.add(list)
        val recyclerView : RecyclerView = layout.findViewById(R.id.recyclerView)
        val listAdapter = BasicAdapter(albumList,this, 2)
        recyclerView.adapter = listAdapter
    }
}