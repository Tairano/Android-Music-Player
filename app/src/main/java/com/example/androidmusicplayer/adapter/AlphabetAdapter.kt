package com.example.androidmusicplayer.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.struct.Play

class AlphabetAdapter (private val dataSet: ArrayList<Play>, val context: Fragment) :
    RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val index : TextView = view.findViewById(R.id.index)
        val name : TextView = view.findViewById(R.id.name)
        val author : TextView = view.findViewById(R.id.author)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.play_card, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val play = dataSet[position]
            val intent = Intent("PlayPageActivity")
            intent.putExtra("play",play)
            context.startActivity(intent)
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val play = dataSet[position]
        holder.index.text = (position + 1).toString()
        holder.author.text = "-" + play.author
        holder.name.text = play.name
    }

    override fun getItemCount() = dataSet.size
}
