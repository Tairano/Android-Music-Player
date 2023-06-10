package com.example.androidmusicplayer.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.struct.BasicList

class BasicAdapter(private val dataSet: ArrayList<BasicList>, val context: Fragment, val status: Int) :
    RecyclerView.Adapter<BasicAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listImage : ImageView = view.findViewById(R.id.list_image)
        val playListName : TextView = view.findViewById(R.id.name)
        val context : TextView = view.findViewById(R.id.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.play_list_card, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val playList = dataSet[position]
            val intent = Intent("ListActivity")
            intent.putExtra("list",playList)
            context.startActivity(intent)
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playList = dataSet[position]
        if(status == 3){
            holder.listImage.setImageResource(R.drawable.folder)
        }
        else{
            holder.listImage.setImageBitmap(playList.getFirstSongCover())
        }
        holder.playListName.text = playList.str
        holder.context.text = playList.getSize().toString() + "首     " + playList.content
    }

    override fun getItemCount() = dataSet.size
}