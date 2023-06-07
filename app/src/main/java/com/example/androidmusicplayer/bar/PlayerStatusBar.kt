package com.example.androidmusicplayer.bar

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.struct.PlayService

class PlayerStatusBar : Fragment() {
    private lateinit var layout : View
    private lateinit var  songName : TextView
    private lateinit var  author : TextView
    private lateinit var  playButton : Button
    private lateinit var binder : PlayService.PlayBinder

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as PlayService.PlayBinder
            binder.getListener().addAuthorView(author)
            binder.getListener().addNameView(songName)
            binder.getListener().addPlayButton(playButton)
            binder.getListener().refresh()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder.getListener().removeAuthorView(author)
            binder.getListener().removeNameView(songName)
            binder.getListener().removePlayButton(playButton)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initPage(){
        songName = layout.findViewById(R.id.song_name)
        author  = layout.findViewById(R.id.author)
        playButton = layout.findViewById(R.id.button_play)

        playButton.setOnClickListener { binder.pause() }

        val intents = Intent(this.activity, PlayService::class.java)
        activity?.bindService(intents, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_player_status_bar, container, false)
        initPage()
        return layout
    }
}