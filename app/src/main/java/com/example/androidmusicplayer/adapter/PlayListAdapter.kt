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
import com.example.androidmusicplayer.media.byteArrayToBitmap
import com.example.androidmusicplayer.struct.PlayList

class PlayListAdapter(private val dataSet: ArrayList<PlayList>, val context: Fragment) :
    RecyclerView.Adapter<PlayListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.image)
        val name : TextView = view.findViewById(R.id.name)
        val comment : TextView = view.findViewById(R.id.comment)
        val size : TextView = view.findViewById(R.id.size)
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
        if(playList.bitmap != null)
            holder.image.setImageBitmap(byteArrayToBitmap(playList.bitmap))
        else
            holder.image.setImageResource(R.drawable.logo)
        holder.name.text = playList.name
        holder.comment.text = playList.comment
        holder.size.text = playList.size.toString() + "首     "
    }

    override fun getItemCount() = dataSet.size
}