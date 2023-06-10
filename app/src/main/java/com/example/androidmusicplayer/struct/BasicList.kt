package com.example.androidmusicplayer.struct

import android.graphics.Bitmap
import android.util.Log
import java.io.Serializable

open class BasicList(val str: String, val content: String) : Serializable {

    private val list = ArrayList<Play>()

    fun getSize() = list.size

    fun getItem(i: Int): Play? {
        if(i >=0 && i < list.size)
            return list[i]
        return null
    }

    fun getList(): ArrayList<Play> = list

    fun sortIt() {
        list.sortByDescending { play -> play.name[0] }
    }

    fun add(play: Play) = list.add(play)

    fun remove(i: Play) = list.remove(i)

    fun getFirstSongCover(): Bitmap?{
        for(i in list){
            if(i.bitMap != null){
                Log.d("SUCCESS FIND!", "!!!!!!!!!!!!!!!!!!!")
                return i.bitMap
            }
        }
        return null
    }
}