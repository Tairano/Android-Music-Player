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
import com.example.androidmusicplayer.struct.Folder
import com.example.androidmusicplayer.struct.Play

class FolderFragment : Fragment() {
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
        val folderList = ArrayList<BasicList>()
        var list = Folder("8080","localhost:8080/")
        var play = Play("写给黄淮","邵帅")
        list.add(play)
        folderList.add(list)
        list = Folder("music","storage/src/music")
        play = Play("写给黄淮","邵帅")
        list.add(play)
        folderList.add(list)
        val recyclerView : RecyclerView = layout.findViewById(R.id.recyclerView)
        val listAdapter = BasicAdapter(folderList,this)
        recyclerView.adapter = listAdapter
    }

}