package com.example.androidmusicplayer.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.AlphabetAdapter
import com.example.androidmusicplayer.struct.Play
import com.example.androidmusicplayer.struct.PlayList

class SingleFragment : Fragment() {
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
        val playList = PlayList("",R.drawable.logo)
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        playList.add(Play("a12","213"))
        val recyclerView : RecyclerView = layout.findViewById(R.id.recyclerView)
        val listAdapter = AlphabetAdapter(playList.getList(),this)
        recyclerView.adapter = listAdapter
    }
}