package com.example.androidmusicplayer.struct

import java.io.Serializable

class Play(var name: String, var author: String): Serializable {
    lateinit var fileName : String
    lateinit var path : String
    var playLocation : Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Play

        if (name != other.name) return false
        if (author != other.author) return false
        if (fileName != other.fileName) return false
        if (path != other.path) return false
        if (playLocation != other.playLocation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + playLocation
        return result
    }

}