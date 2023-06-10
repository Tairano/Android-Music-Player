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
        val intent = activity?.intent
        val playList = intent?.getSerializableExtra("singerList") as ArrayList<BasicList>
        val recyclerView : RecyclerView = layout.findViewById(R.id.recyclerView)
        val listAdapter = BasicAdapter(playList,this, 0)
        recyclerView.adapter = listAdapter
    }
}