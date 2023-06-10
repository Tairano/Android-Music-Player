package com.example.androidmusicplayer.struct

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever

class PlayList (val name: String): BasicList(name, "") {
    private val list = ArrayList<Play>()

    var timeStamp : String = ""
    var comment : String = ""

    fun searchToList(str: String): ArrayList<Play> {
        if(name.isEmpty())
            return list
        var list = ArrayList<Play>()
        for(i in 0 until this.list.size) {
            if(this.list[i].name.contains(str) || this.list[i].author.contains(str))
                list.add(this.list[i])
        }
        return list
    }

}