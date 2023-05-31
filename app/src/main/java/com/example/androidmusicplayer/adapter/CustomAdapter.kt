package com.example.androidmusicplayer.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.androidmusicplayer.struct.PlayList
import com.example.androidmusicplayer.R


class CustomAdapter(private val dataSet: ArrayList<PlayList>, val context: Fragment) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listImage : ImageView = view.findViewById(R.id.list_image)
        val playListName : TextView = view.findViewById(R.id.name)
        val songNumber : TextView = view.findViewById(R.id.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.play_list_card, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val playList = dataSet[position]
            val intent = Intent("PlayListActivity")
            intent.putExtra("list",playList)
            context.startActivity(intent)
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playList = dataSet[position]
        holder.listImage.setImageResource(playList.imageId)
        holder.playListName.text = playList.name
        holder.songNumber.text = playList.getSize().toString() + "首"
    }

    override fun getItemCount() = dataSet.size
}
