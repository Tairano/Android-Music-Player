package com.example.androidmusicplayer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.struct.Play
import com.example.androidmusicplayer.R

class PlayAdapter(private val dataSet: ArrayList<Play>, val context: Context) :
    RecyclerView.Adapter<PlayAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val index : TextView = view.findViewById(R.id.index)
        val name : TextView = view.findViewById(R.id.name)
        val author : TextView = view.findViewById(R.id.author)
        val album : TextView = view.findViewById(R.id.album)
        val more : Button = view.findViewById(R.id.more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.play_card, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val play = dataSet[position]
            val intent = Intent("PlayPageActivity")
            intent.putExtra("playPath",play.path)
            context.startActivity(intent)
        }
        return viewHolder
    }

    private fun popUpMenu(v: View?){
        val popupMenu = PopupMenu(context,v)
        popupMenu.inflate(R.menu.play_more_menu)
        popupMenu.setOnMenuItemClickListener{ item ->
            when( item.itemId ){
                R.id.add_in_next ->{
                    Toast.makeText(context, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.add_in_list ->{
                    Toast.makeText(context, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val play = dataSet[position]
        holder.index.text = (position + 1).toString()
        holder.author.text = play.artist
        holder.album.text = "-" + play.album
        holder.name.text = play.title
        holder.more.setOnClickListener {
            popUpMenu(holder.more)
        }
    }

    override fun getItemCount() = dataSet.size
}
