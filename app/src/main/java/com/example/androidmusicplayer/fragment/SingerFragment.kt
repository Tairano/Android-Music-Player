package com.example.androidmusicplayer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.BasicAdapter
import com.example.androidmusicplayer.struct.BasicList
import com.example.androidmusicplayer.struct.Play
import com.example.androidmusicplayer.struct.Singer

class SingerFragment : Fragment() {
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
        val singerList = ArrayList<BasicList>()
        var list = Singer("邵帅", R.mipmap.ic_launcher)
        var play = Play("写给黄淮","邵帅")
        list.add(play)
        singerList.add(list)
        list = Singer("宋东野", R.mipmap.ic_launcher)
        play = Play("斑马","宋东野")
        list.add(play)
        singerList.add(list)
        val recyclerView : RecyclerView = layout.findViewById(R.id.recyclerView)
        val listAdapter = BasicAdapter(singerList,this)
        recyclerView.adapter = listAdapter
    }
}