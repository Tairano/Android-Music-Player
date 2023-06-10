package com.example.androidmusicplayer.struct

import android.graphics.Bitmap
import java.io.Serializable

class Play(var name: String, var author: String): Serializable {
    lateinit var fileName : String
    lateinit var path : String
    var bitMap : Bitmap? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Play

        if (name != other.name) return false
        if (author != other.author) return false
        if (fileName != other.fileName) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }

}