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
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.media.byteArrayToBitmap
import com.example.androidmusicplayer.struct.LocalStorage


class LocalAdapter(private val dataSet: ArrayList<LocalStorage>, val context: Fragment) :
    RecyclerView.Adapter<LocalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = view.findViewById(R.id.image)
        val name : TextView = view.findViewById(R.id.name)
        val size : TextView = view.findViewById(R.id.size)
        val comment : TextView = view.findViewById(R.id.comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.play_list_card, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val localStorage = dataSet[position]
            val intent = Intent("ListActivity")
            intent.putExtra("queryName",localStorage.name)
            intent.putExtra("queryType",localStorage.type)
            context.startActivity(intent)
        }
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val localStorage = dataSet[position]
        if(localStorage.bitMap != null)
            holder.image.setImageBitmap(
                byteArrayToBitmap(localStorage.bitMap))
        else{
            holder.image.setImageResource(R.drawable.logo)
        }
        holder.name.text = localStorage.name
        holder.comment.text = localStorage.comment
        holder.size.text = localStorage.size.toString() + "首"
    }

    override fun getItemCount() = dataSet.size
}
