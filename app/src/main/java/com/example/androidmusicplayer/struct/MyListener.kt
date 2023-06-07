package com.example.androidmusicplayer.struct

import android.widget.Button
import android.widget.TextView
import com.example.androidmusicplayer.R


val PLAY_STATUS = arrayOf(
    0 to R.drawable.play,
    1 to R.drawable.stop
)

val PLAY_TYPE_STATUS = arrayOf(
    0 to R.drawable.one_recycle,
    1 to R.drawable.hole_recycle,
    2 to R.drawable.radom
)

class MyListener {
    private val playButtonList = ArrayList<Button>()
    private val playTypeButtonList = ArrayList<Button>()
    private val nameList = ArrayList<TextView>()
    private val authorList = ArrayList<TextView>()

    var playStatus = 1
    var playTypeStatus = 2
    var name = ""
    var author = ""

    fun refresh(){
        refreshPlayButton()
        refreshPlayTypeButton()
        refreshNameText()
        refreshAuthorText()
    }

    private fun refreshPlayButton(){
        for(i in playButtonList){
            i.setBackgroundResource(PLAY_STATUS[playStatus].second)
        }
    }

    private fun refreshPlayTypeButton(){
        for(i in playTypeButtonList){
            i.setBackgroundResource(PLAY_TYPE_STATUS[playTypeStatus].second)
        }
    }

    private fun refreshNameText(){
        for(i in nameList){
            i.text = name
        }
    }

    private fun refreshAuthorText(){
        for(i in authorList){
            i.text = author
        }
    }

    fun addPlayButton(button: Button){
        playButtonList.add(button)
    }

    fun removePlayButton(button: Button){
        playButtonList.remove(button)
    }

    fun addPlayTypeButton(button: Button){
        playTypeButtonList.add(button)
    }

    fun removePlayTypeButton(button: Button){
        playTypeButtonList.remove(button)
    }

    fun addNameView(view: TextView){
        nameList.add(view)
    }

    fun removeNameView(view: TextView){
        nameList.remove(view)
    }

    fun addAuthorView(view: TextView){
        authorList.add(view)
    }

    fun removeAuthorView(view: TextView){
        authorList.remove(view)
    }
}