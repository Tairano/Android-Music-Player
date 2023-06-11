package com.example.androidmusicplayer.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
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
            intent.putExtra("play",play)
            context.startActivity(intent)
        }
        return viewHolder
    }

    private fun popUpMenu(v: View?){
        val popupMenu = PopupMenu(context.activity,v)
        popupMenu.inflate(R.menu.play_more_menu)
        popupMenu.setOnMenuItemClickListener{ item ->
            when( item.itemId ){
                R.id.add_in_next ->{
                    Toast.makeText(context.activity, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.add_in_list ->{
                    Toast.makeText(context.activity, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
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
//        holder.author.text = "-" + play.author
//        holder.name.text = play.name
        holder.more.setOnClickListener {
            popUpMenu(holder.more)
        }
    }

    override fun getItemCount() = dataSet.size
}
