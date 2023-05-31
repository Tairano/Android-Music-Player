package com.example.androidmusicplayer.struct

import java.io.Serializable

open class BasicList(val str: String, val content: String, val imgId: Int) : Serializable {

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
}